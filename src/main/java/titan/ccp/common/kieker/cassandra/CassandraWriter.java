package titan.ccp.common.kieker.cassandra;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

import org.apache.commons.math3.util.Pair;

import com.datastax.driver.core.Session;
import com.datastax.driver.core.Statement;
import com.datastax.driver.core.querybuilder.Insert;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import com.datastax.driver.core.schemabuilder.Create;
import com.datastax.driver.core.schemabuilder.SchemaBuilder;
import com.google.common.base.Objects;
import com.google.common.collect.Streams;

import kieker.analysisteetime.util.ComposedKey;
import kieker.common.record.IMonitoringRecord;
import titan.ccp.common.kieker.ArrayValueSerializer;
import titan.ccp.models.records.PowerConsumptionRecord;

public class CassandraWriter {

	private static final String RECORD_TYPE_NAME = "recordType";
	private static final Class<?> RECORD_TYPE_TYPE = String.class;

	private static final String LOGGING_TIMESTAMP_NAME = "loggingTimestamp";
	private static final Class<?> LOGGING_TIMESTAMP_TYPE = long.class;

	private final Session session;
	
	private final Function<IMonitoringRecord, String> tableNameMapper;
	
	private final PrimaryKeySelectionStrategy primaryKeySelectionStrategy;
	
	private final boolean includeRecordType;

	private final boolean includeLoggingTimestamp;

	private final boolean executeAsync;

	private final Set<String> existingTables = new HashSet<>();

//	public CassandraWriter(final Session session) {
//		// TODO Auto-generated constructor stub
//		// final String host = "";
//		// final int port = 0;
//		// final String keyspace = "";
//		//
//		// final Cluster cluster =
//		// Cluster.builder().addContactPoint(host).withPort(port).build();
//		// this.session = cluster.connect(keyspace);
//		this.session = session;
//
//	}
//	
	public CassandraWriter(Session session, Function<IMonitoringRecord, String> tableNameMapper,
			PrimaryKeySelectionStrategy primaryKeySelectionStrategy, boolean includeRecordType,
			boolean includeLoggingTimestamp, boolean executeAsync) {
		this.session = session;
		this.tableNameMapper = tableNameMapper;
		this.primaryKeySelectionStrategy = primaryKeySelectionStrategy;
		this.includeRecordType = includeRecordType;
		this.includeLoggingTimestamp = includeLoggingTimestamp;
		this.executeAsync = executeAsync;
	}



	public void write(final IMonitoringRecord record) {
		final String tableName = this.tableNameMapper.apply(record);

		this.createTableIfNotExists(tableName, record);

		this.store(tableName, record);
	}

	private void createTableIfNotExists(final String tableName, final IMonitoringRecord record) {
		if (!this.existingTables.contains(tableName)) {
			this.createTable(tableName, record);
			this.existingTables.add(tableName);
		}
	}

	private void createTable(final String tableName, final IMonitoringRecord record) {
		final List<String> includedFields = this.getFields(record);
		final List<Class<?>> includedFieldTypes = this.getFieldTypes(record);

		final Set<String> partitionKey = this.primaryKeySelectionStrategy.selectPartitionKeys(tableName, includedFields);
		final Set<String> clusteringColumns = this.primaryKeySelectionStrategy.selectClusteringColumns(tableName, includedFields);

		final Create createStatement = SchemaBuilder.createTable(tableName).ifNotExists();

		Streams.zip(includedFields.stream(), includedFieldTypes.stream(), RecordField::new)
				.forEach(field -> {
					if (partitionKey.contains(field.name)) {
						createStatement.addPartitionKey(field.name, JavaTypeMapper.map(field.type));
					} else if (clusteringColumns.contains(field.name)) {
						createStatement.addClusteringColumn(field.name, JavaTypeMapper.map(field.type));
					} else {
						createStatement.addColumn(field.name, JavaTypeMapper.map(field.type));
					}
				});

		this.session.execute(createStatement);
	}

	private void store(final String table, final IMonitoringRecord record) {
		final String[] valueNames = record.getValueNames();
		final Object[] values = new Object[valueNames.length];
		record.serialize(new ArrayValueSerializer(values));

		final Insert insertStatement = QueryBuilder.insertInto(table);
		if (this.includeRecordType) {
			insertStatement.value(RECORD_TYPE_NAME, record.getClass().getName());
		}
		if (this.includeLoggingTimestamp) {
			insertStatement.value(LOGGING_TIMESTAMP_NAME, record.getLoggingTimestamp());
		}
		insertStatement.values(valueNames, values);

		this.executeStatement(insertStatement);
	}

	private List<String> getFields(final IMonitoringRecord record) {
		final String[] valueNames = record.getValueNames();

		final List<String> fields = new ArrayList<>(valueNames.length + 2);
		if (this.includeRecordType) {
			fields.add(RECORD_TYPE_NAME);
		}
		if (this.includeLoggingTimestamp) {
			fields.add(LOGGING_TIMESTAMP_NAME);
		}
		Collections.addAll(fields, valueNames);
		return fields;
	}

	private List<Class<?>> getFieldTypes(final IMonitoringRecord record) {
		final Class<?>[] valueTypes = record.getValueTypes();

		final List<Class<?>> fieldTypes = new ArrayList<>(valueTypes.length + 2);
		if (this.includeRecordType) {
			fieldTypes.add(RECORD_TYPE_TYPE);
		}
		if (this.includeLoggingTimestamp) {
			fieldTypes.add(LOGGING_TIMESTAMP_TYPE);
		}
		Collections.addAll(fieldTypes, valueTypes);
		return fieldTypes;
	}

	private void executeStatement(final Statement statement) {
		if (this.executeAsync) {
			this.session.executeAsync(statement);
		} else {
			this.session.execute(statement);
		}

	}
	
	public static Builder builder(Session session) {
		return new Builder(session);
	}
	
	public static class Builder {

		private final Session session;
		
		private Function<IMonitoringRecord, String> tableNameMapper = PredefinedTableNameMappers.CLASS_NAME;
		
		private PrimaryKeySelectionStrategy primaryKeySelectionStrategy = new TakeLoggingTimestampStrategy();
		
		private boolean includeRecordType = false;

		private boolean includeLoggingTimestamp = true;

		private boolean executeAsync = false;

		public Builder(Session session) {
			this.session = session;
		}
		
		public Builder tableNameMapper(Function<IMonitoringRecord, String> tableNameMapper) {
			this.tableNameMapper = tableNameMapper;
			return this;
		}
		
		public Builder primaryKeySelectionStrategy(PrimaryKeySelectionStrategy strategy) {
			this.primaryKeySelectionStrategy = strategy;
			return this;
		}
		
		public Builder includeRecordType() {
			this.includeRecordType = true;
			return this;
		}
		
		public Builder excludeRecordType() {
			this.includeRecordType = false;
			return this;
		}
		
		public Builder includeLoggingTimestamp() {
			this.includeLoggingTimestamp = true;
			return this;
		}
		
		public Builder excludeLoggingTimestamp() {
			this.includeLoggingTimestamp = false;
			return this;
		}
		
		public Builder async() {
			this.executeAsync = true;
			return this;
		}
		
		public CassandraWriter build() {
			return new CassandraWriter(session, tableNameMapper, primaryKeySelectionStrategy, includeRecordType, includeLoggingTimestamp, executeAsync);
		}
		
	}
	
	private static final class RecordField {

		public final String name;
		public final Class<?> type;

		public RecordField(final String name, final Class<?> type) {
			this.name = name;
			this.type = type;
		}

	}

	public static void main(final String[] args) {
		final PowerConsumptionRecord record = new PowerConsumptionRecord("my-sensor", 12345678, 42);
		// new CassandraWriter().write(record);

	}

}
