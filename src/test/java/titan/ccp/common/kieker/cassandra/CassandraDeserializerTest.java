package titan.ccp.common.kieker.cassandra;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

import com.datastax.driver.core.DataType;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.querybuilder.Insert;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import com.datastax.driver.core.querybuilder.Select;
import com.datastax.driver.core.schemabuilder.Create;
import com.datastax.driver.core.schemabuilder.SchemaBuilder;

import kieker.common.record.flow.trace.operation.BeforeOperationEvent;
import kieker.common.record.flow.trace.operation.BeforeOperationEventFactory;

public class CassandraDeserializerTest extends AbstractCassandraTest {

	@Test
	public void testBeforeOperationEvent() {
		// Create Table
		final Create createStatement = SchemaBuilder.createTable("records")
				.addPartitionKey("timestamp", DataType.bigint())
				.addColumn("traceId", DataType.bigint())
				.addColumn("orderIndex", DataType.cint())
				.addColumn("operationSignature", DataType.text())
				.addColumn("classSignature", DataType.text());
		this.session.execute(createStatement);

		// Insert Record
		final Insert insertStatement = QueryBuilder.insertInto("records")
				.value("timestamp", 1525433696)
				.value("traceId", 7)
				.value("orderIndex", 42)
				.value("operationSignature", "doSomething()")
				.value("classSignature", "ClassName");
		this.session.execute(insertStatement);

		final Select selectStatement = QueryBuilder.select("timestamp", "traceId", "orderIndex", "operationSignature", "classSignature").from("records");
		final ResultSet resultSet = this.session.execute(selectStatement);
		final List<Row> records = resultSet.all();
		assertEquals(1, records.size());
		final BeforeOperationEventFactory factory = new BeforeOperationEventFactory();
		final String[] beforeOperationEventColumnNames = new BeforeOperationEvent(0, 0, 0, "", "").getValueNames();
		final BeforeOperationEvent beforeOperationEvent = factory.create(new CassandraDeserializer(records.get(0), beforeOperationEventColumnNames));
		assertEquals(1525433696, beforeOperationEvent.getTimestamp());
		assertEquals(7, beforeOperationEvent.getTraceId());
		assertEquals(42, beforeOperationEvent.getOrderIndex());
		assertEquals("doSomething()", beforeOperationEvent.getOperationSignature());
		assertEquals("ClassName", beforeOperationEvent.getClassSignature());
	}

}
