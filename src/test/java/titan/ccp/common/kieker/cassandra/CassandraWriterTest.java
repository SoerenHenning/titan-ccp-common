package titan.ccp.common.kieker.cassandra;

import static org.junit.Assert.*;

import java.util.List;

import org.cassandraunit.utils.EmbeddedCassandraServerHelper;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.DataType;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.querybuilder.Insert;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import com.datastax.driver.core.querybuilder.Select;
import com.datastax.driver.core.schemabuilder.Create;
import com.datastax.driver.core.schemabuilder.DropKeyspace;
import com.datastax.driver.core.schemabuilder.KeyspaceOptions;
import com.datastax.driver.core.schemabuilder.SchemaBuilder;
import com.google.common.collect.ImmutableMap;

public class CassandraWriterTest {

	private static final String KEYSPACE = "test";

	private static Cluster cluster;
	private Session session;
	
	@BeforeClass
	public static void setUpClass() throws Exception {
		EmbeddedCassandraServerHelper.startEmbeddedCassandra();
		
		CassandraWriterTest.cluster = Cluster.builder()
				.addContactPoint(EmbeddedCassandraServerHelper.getHost())
				.withPort(EmbeddedCassandraServerHelper.getNativeTransportPort())
				.build();
	}
	
	@AfterClass
	public static void tearDownClass() throws Exception {
		cluster.close();
	}
	
	@Before
	public void setUp() throws Exception {
		final Session session = EmbeddedCassandraServerHelper.getSession();
		final KeyspaceOptions createKeyspace = SchemaBuilder.createKeyspace(KEYSPACE)
				.ifNotExists()
				.with().replication(ImmutableMap.of("class", "SimpleStrategy", "replication_factor", 1));
		session.execute(createKeyspace);
		
		this.session = cluster.connect(KEYSPACE);
	}

	@After
	public void tearDown() throws Exception {
		this.session.close();
		this.session = null;
		
		final Session session = EmbeddedCassandraServerHelper.getSession();
		final DropKeyspace dropKeyspace = SchemaBuilder.dropKeyspace(KEYSPACE).ifExists();
		session.execute(dropKeyspace);
	}

	@Test
	public void testCassandraRunning() {
		final String tableName = "testtable";
		final String keyName = "key";
		final int keyValue = 3;
		
		final Create create = SchemaBuilder.createTable(tableName).ifNotExists().addPartitionKey(keyName, DataType.cint());
		this.session.execute(create);

		final Insert insert = QueryBuilder.insertInto(tableName).value(keyName, keyValue);
		this.session.execute(insert);

		final Select select = QueryBuilder.select(keyName).from(tableName);
		final ResultSet resultSet = this.session.execute(select);
		final List<Row> rows = resultSet.all();

		assertEquals(1, rows.size());
		assertEquals(keyValue, rows.get(0).getInt(keyName));
	}
	

	/**
	 * We are testing this two times to ensure that single tests do not influence each other.
	 */
	@Test
	public void testCassandraRunningSecondTime() {
		final String tableName = "testtable";
		final String keyName = "key";
		final int keyValue = 3;

		final Create create = SchemaBuilder.createTable(tableName).ifNotExists().addPartitionKey(keyName, DataType.cint());
		this.session.execute(create);

		final Insert insert = QueryBuilder.insertInto(tableName).value(keyName, keyValue);
		this.session.execute(insert);

		final Select select = QueryBuilder.select(keyName).from(tableName);
		final ResultSet resultSet = this.session.execute(select);
		final List<Row> rows = resultSet.all();

		assertEquals(1, rows.size());
		assertEquals(keyValue, rows.get(0).getInt(keyName));
	}
	
	

}
