package titan.ccp.common.kieker.cassandra;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;
import titan.ccp.common.cassandra.AbstractCassandraTest;
import titan.ccp.common.cassandra.ExplicitPrimaryKeySelectionStrategy;
import titan.ccp.common.cassandra.PredefinedTableNameMappers;
import com.datastax.driver.core.DataType;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.querybuilder.Insert;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import com.datastax.driver.core.querybuilder.Select;
import com.datastax.driver.core.schemabuilder.Create;
import com.datastax.driver.core.schemabuilder.SchemaBuilder;

import kieker.common.record.flow.trace.operation.BeforeOperationEvent;

public class KiekerCassandraWriterTest extends AbstractCassandraTest {

	@Test
	public void testCassandraRunning() {
		final String tableName = "testtable";
		final String keyName = "key";
		final int keyValue = 3;

		final Create create = SchemaBuilder.createTable(tableName).ifNotExists().addPartitionKey(keyName,
				DataType.cint());
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
	 * We are testing this two times to ensure that single tests do not influence
	 * each other.
	 */
	@Test
	public void testCassandraRunningSecondTime() {
		final String tableName = "testtable";
		final String keyName = "key";
		final int keyValue = 3;

		final Create create = SchemaBuilder.createTable(tableName).ifNotExists().addPartitionKey(keyName,
				DataType.cint());
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
		final BeforeOperationEvent record = new BeforeOperationEvent(12345, 123, 1, "foo()", "project.package.Class");

		final ExplicitPrimaryKeySelectionStrategy primaryKeySelectionStrategy = new ExplicitPrimaryKeySelectionStrategy();
		primaryKeySelectionStrategy.registerPartitionKeys("BeforeOperationEvent", "timestamp");

		final KiekerCassandraWriter cassandraWriter = KiekerCassandraWriter.builder(this.session)
				.tableNameMapper(PredefinedTableNameMappers.SIMPLE_CLASS_NAME).excludeRecordType()
				.excludeLoggingTimestamp().primaryKeySelectionStrategy(primaryKeySelectionStrategy).build();

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
		final BeforeOperationEvent record = new BeforeOperationEvent(12345, 123, 1, "foo()", "project.package.Class");

		final ExplicitPrimaryKeySelectionStrategy primaryKeySelectionStrategy = new ExplicitPrimaryKeySelectionStrategy();
		primaryKeySelectionStrategy.registerPartitionKeys("BeforeOperationEvent", "timestamp");

		final KiekerCassandraWriter cassandraWriter = KiekerCassandraWriter.builder(this.session)
				.tableNameMapper(PredefinedTableNameMappers.SIMPLE_CLASS_NAME).includeRecordType()
				.excludeLoggingTimestamp().primaryKeySelectionStrategy(primaryKeySelectionStrategy).build();

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
		final BeforeOperationEvent record = new BeforeOperationEvent(12345, 123, 1, "foo()", "project.package.Class");

		final KiekerCassandraWriter cassandraWriter = KiekerCassandraWriter.builder(this.session)
				.tableNameMapper(PredefinedTableNameMappers.SIMPLE_CLASS_NAME).excludeRecordType()
				.includeLoggingTimestamp().build();

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
		final BeforeOperationEvent record = new BeforeOperationEvent(12345, 123, 1, "foo()", "project.package.Class");

		final KiekerCassandraWriter cassandraWriter = KiekerCassandraWriter.builder(this.session)
				.tableNameMapper(PredefinedTableNameMappers.SIMPLE_CLASS_NAME).includeRecordType()
				.includeLoggingTimestamp().build();

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