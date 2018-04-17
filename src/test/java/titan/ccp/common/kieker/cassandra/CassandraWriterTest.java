package titan.ccp.common.kieker.cassandra;

import static org.junit.Assert.*;

import java.util.List;

import org.cassandraunit.utils.EmbeddedCassandraServerHelper;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
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

import kieker.common.record.flow.trace.operation.BeforeOperationEvent;

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
	
	@Test
	public void testWithoutRecordtypeAndTimestamp() {
		BeforeOperationEvent record = new BeforeOperationEvent(12345, 123, 1, "foo()", "project.package.Class");
		
		ExplicitPrimaryKeySelectionStrategy primaryKeySelectionStrategy = new ExplicitPrimaryKeySelectionStrategy();
		primaryKeySelectionStrategy.registerPartitionKeys("BeforeOperationEvent", "timestamp");
		
		CassandraWriter cassandraWriter = CassandraWriter
				.builder(session)
				.tableNameMapper(PredefinedTableNameMappers.SIMPLE_CLASS_NAME)
				.excludeRecordType()
				.excludeLoggingTimestamp()
				.primaryKeySelectionStrategy(primaryKeySelectionStrategy)
				.build();
		
		cassandraWriter.write(record);
		
		final Select select = QueryBuilder.select().all().from("BeforeOperationEvent");
		final ResultSet resultSet = this.session.execute(select);
		final List<Row> rows = resultSet.all();
		
		assertEquals(1, rows.size());
		
		final Row row = rows.get(0);
		
		assertEquals(record.getTimestamp(), row.getLong("timestamp"));
		assertEquals(record.getTraceId(), row.getLong("traceId"));
		assertEquals(record.getOrderIndex(), row.getInt("orderIndex"));
		assertEquals(record.getOperationSignature(), row.getString("operationSignature"));
		assertEquals(record.getClassSignature(), row.getString("classSignature"));
		
	}
	
	@Test
	public void testWithRecordtypeAndWithoutTimestamp() {
		BeforeOperationEvent record = new BeforeOperationEvent(12345, 123, 1, "foo()", "project.package.Class");
		
		ExplicitPrimaryKeySelectionStrategy primaryKeySelectionStrategy = new ExplicitPrimaryKeySelectionStrategy();
		primaryKeySelectionStrategy.registerPartitionKeys("BeforeOperationEvent", "timestamp");
		
		CassandraWriter cassandraWriter = CassandraWriter
				.builder(session)
				.tableNameMapper(PredefinedTableNameMappers.SIMPLE_CLASS_NAME)
				.includeRecordType()
				.excludeLoggingTimestamp()
				.primaryKeySelectionStrategy(primaryKeySelectionStrategy)
				.build();
		
		cassandraWriter.write(record);
		
		final Select select = QueryBuilder.select().all().from("BeforeOperationEvent");
		final ResultSet resultSet = this.session.execute(select);
		final List<Row> rows = resultSet.all();
		
		assertEquals(1, rows.size());
		
		final Row row = rows.get(0);
		
		assertEquals(record.getClass().getName(), row.getString("recordType"));
		assertEquals(record.getTimestamp(), row.getLong("timestamp"));
		assertEquals(record.getTraceId(), row.getLong("traceId"));
		assertEquals(record.getOrderIndex(), row.getInt("orderIndex"));
		assertEquals(record.getOperationSignature(), row.getString("operationSignature"));
		assertEquals(record.getClassSignature(), row.getString("classSignature"));
		
	}
	
	@Test
	public void testWithoutRecordtypeAndWithTimestamp() {
		BeforeOperationEvent record = new BeforeOperationEvent(12345, 123, 1, "foo()", "project.package.Class");
		
		CassandraWriter cassandraWriter = CassandraWriter
				.builder(session)
				.tableNameMapper(PredefinedTableNameMappers.SIMPLE_CLASS_NAME)
				.excludeRecordType()
				.includeLoggingTimestamp()
				.build();
		
		cassandraWriter.write(record);
		
		final Select select = QueryBuilder.select().all().from("BeforeOperationEvent");
		final ResultSet resultSet = this.session.execute(select);
		final List<Row> rows = resultSet.all();
		
		assertEquals(1, rows.size());
		
		final Row row = rows.get(0);
		
		assertEquals(record.getLoggingTimestamp(), row.getLong("loggingTimestamp"));
		assertEquals(record.getTimestamp(), row.getLong("timestamp"));
		assertEquals(record.getTraceId(), row.getLong("traceId"));
		assertEquals(record.getOrderIndex(), row.getInt("orderIndex"));
		assertEquals(record.getOperationSignature(), row.getString("operationSignature"));
		assertEquals(record.getClassSignature(), row.getString("classSignature"));
		
	}
	
	@Test
	public void testWithRecordtypeAndTimestamp() {
		BeforeOperationEvent record = new BeforeOperationEvent(12345, 123, 1, "foo()", "project.package.Class");
		
		CassandraWriter cassandraWriter = CassandraWriter
				.builder(session)
				.tableNameMapper(PredefinedTableNameMappers.SIMPLE_CLASS_NAME)
				.includeRecordType()
				.includeLoggingTimestamp()
				.build();
		
		cassandraWriter.write(record);
		
		final Select select = QueryBuilder.select().all().from("BeforeOperationEvent");
		final ResultSet resultSet = this.session.execute(select);
		final List<Row> rows = resultSet.all();
		
		assertEquals(1, rows.size());
		
		final Row row = rows.get(0);
		
		assertEquals(record.getClass().getName(), row.getString("recordType"));
		assertEquals(record.getLoggingTimestamp(), row.getLong("loggingTimestamp"));
		assertEquals(record.getTimestamp(), row.getLong("timestamp"));
		assertEquals(record.getTraceId(), row.getLong("traceId"));
		assertEquals(record.getOrderIndex(), row.getInt("orderIndex"));
		assertEquals(record.getOperationSignature(), row.getString("operationSignature"));
		assertEquals(record.getClassSignature(), row.getString("classSignature"));
		
	}

}
