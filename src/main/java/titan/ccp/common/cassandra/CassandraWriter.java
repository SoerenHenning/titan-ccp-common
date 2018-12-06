package titan.ccp.common.cassandra;

import com.datastax.driver.core.Session;
import com.datastax.driver.core.Statement;
import com.datastax.driver.core.querybuilder.Insert;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import com.datastax.driver.core.schemabuilder.Create;
import com.datastax.driver.core.schemabuilder.SchemaBuilder;
import com.google.common.collect.Streams;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

public class CassandraWriter<T> {

  private final Session session;

  private final DataAdapter<T> dataAdapter;

  private final Function<? super T, String> tableNameMapper;

  private final PrimaryKeySelectionStrategy primaryKeySelectionStrategy;

  private final boolean executeAsync;

  private final Set<String> existingTables = new HashSet<>();

  public CassandraWriter(final Session session, final DataAdapter<T> dataAdapter,
      final Function<? super T, String> tableNameMapper,
      final PrimaryKeySelectionStrategy primaryKeySelectionStrategy, final boolean executeAsync) {
    this.session = session;
    this.dataAdapter = dataAdapter;
    this.tableNameMapper = tableNameMapper;
    this.primaryKeySelectionStrategy = primaryKeySelectionStrategy;
    this.executeAsync = executeAsync;
  }

  public void write(final T record) {
    final String tableName = this.tableNameMapper.apply(record);

    this.createTableIfNotExists(tableName, record);

    this.store(tableName, record);
  }

  private void createTableIfNotExists(final String tableName, final T record) {
    if (!this.existingTables.contains(tableName)) {
      this.createTable(tableName, record);
      this.existingTables.add(tableName);
    }
  }

  private void createTable(final String tableName, final T record) {
    final List<String> includedFields = this.dataAdapter.getFieldNames(record);
    final List<Class<?>> includedFieldTypes = this.dataAdapter.getFieldTypes(record);

    final Set<String> partitionKey =
        this.primaryKeySelectionStrategy.selectPartitionKeys(tableName, includedFields);
    final Set<String> clusteringColumns =
        this.primaryKeySelectionStrategy.selectClusteringColumns(tableName, includedFields);

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

  private void store(final String table, final T record) {
    final List<String> fieldNames = this.dataAdapter.getFieldNames(record);
    final List<Object> values = this.dataAdapter.getValues(record);

    final Insert insertStatement = QueryBuilder.insertInto(table).values(fieldNames, values);

    this.executeStatement(insertStatement);
  }

  private void executeStatement(final Statement statement) {
    if (this.executeAsync) {
      this.session.executeAsync(statement);
    } else {
      this.session.execute(statement);
    }

  }

  public static <T> Builder<T> builder(final Session session, final DataAdapter<T> dataAdapter) {
    return new Builder<>(session, dataAdapter);
  }

  public static class Builder<T> {

    private final Session session;

    private final DataAdapter<T> dataAdapter;

    private Function<? super T, String> tableNameMapper = PredefinedTableNameMappers.CLASS_NAME;

    private PrimaryKeySelectionStrategy primaryKeySelectionStrategy =
        new TakeLoggingTimestampStrategy();

    private boolean executeAsync = false;

    public Builder(final Session session, final DataAdapter<T> dataAdapter) {
      this.session = session;
      this.dataAdapter = dataAdapter;
    }

    public Builder<T> tableNameMapper(final Function<? super T, String> tableNameMapper) {
      this.tableNameMapper = tableNameMapper;
      return this;
    }

    public Builder<T> primaryKeySelectionStrategy(final PrimaryKeySelectionStrategy strategy) {
      this.primaryKeySelectionStrategy = strategy;
      return this;
    }

    public Builder<T> async() {
      this.executeAsync = true;
      return this;
    }

    public CassandraWriter<T> build() {
      return new CassandraWriter<>(this.session, this.dataAdapter, this.tableNameMapper,
          this.primaryKeySelectionStrategy, this.executeAsync);
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

}
