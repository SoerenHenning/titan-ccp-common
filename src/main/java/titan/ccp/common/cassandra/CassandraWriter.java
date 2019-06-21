package titan.ccp.common.cassandra;

import com.datastax.driver.core.Session;
import com.datastax.driver.core.Statement;
import com.datastax.driver.core.exceptions.NoHostAvailableException;
import com.datastax.driver.core.exceptions.QueryExecutionException;
import com.datastax.driver.core.querybuilder.Insert;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import com.datastax.driver.core.schemabuilder.Create;
import com.datastax.driver.core.schemabuilder.SchemaBuilder;
import com.google.common.collect.Streams;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CassandraWriter<T> {

  private static final Logger LOGGER = LoggerFactory.getLogger(CassandraWriter.class);

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

    final List<String> partitionKeys =
        this.primaryKeySelectionStrategy.selectPartitionKeys(tableName, includedFields);
    final List<String> clusteringColumns =
        this.primaryKeySelectionStrategy.selectClusteringColumns(tableName, includedFields);
    final Set<String> specialColumns =
        new HashSet<>(partitionKeys.size() + clusteringColumns.size());

    final Create createStatement = SchemaBuilder.createTable(tableName).ifNotExists();

    final Map<String, Class<?>> recordFields =
        Streams.zip(includedFields.stream(), includedFieldTypes.stream(), RecordField::new)
            .collect(Collectors.toMap(field -> field.name, field -> field.type));

    for (final String partitionKey : partitionKeys) {
      Optional.ofNullable(recordFields.get(partitionKey)).ifPresent(type -> {
        createStatement.addPartitionKey(partitionKey, JavaTypeMapper.map(type));
        specialColumns.add(partitionKey);
      });
    }

    for (final String clusteringColumn : clusteringColumns) {
      Optional.ofNullable(recordFields.get(clusteringColumn)).ifPresent(type -> {
        createStatement.addClusteringColumn(clusteringColumn, JavaTypeMapper.map(type));
        specialColumns.add(clusteringColumn);
      });
    }

    for (final Entry<String, Class<?>> recordField : recordFields.entrySet()) {
      if (!specialColumns.contains(recordField.getKey())) {
        createStatement.addColumn(recordField.getKey(), JavaTypeMapper.map(recordField.getValue()));
      }
    }

    this.execute(createStatement);
  }

  /**
   * Execute a cassandra statement, such that connection errors and query errors are handled by the
   * database driver.
   *
   * @param statement the statement to execute.
   */
  private void execute(final Statement statement) {
    try {
      this.session.execute(statement);
    } catch (final NoHostAvailableException e) {
      LOGGER.error("Could not connect to cassandra-database", e);
    } catch (final QueryExecutionException e) {
      LOGGER.error("Could not execute cassandra query", e);
    }
  }

  /**
   * Execute a cassandra statement asynchronously, such that connection errors and query errors are
   * handled by the database driver.
   *
   * @param statement the statement to execute.
   */
  private void executeAsync(final Statement statement) {
    try {
      this.session.executeAsync(statement);
    } catch (final NoHostAvailableException e) {
      LOGGER.error("Could not connect to cassandra-database", e);
    } catch (final QueryExecutionException e) {
      LOGGER.error("Could not execute cassandra query", e);
    }
  }

  private void store(final String table, final T record) {
    final List<String> fieldNames = this.dataAdapter.getFieldNames(record);
    final List<Object> values = this.dataAdapter.getValues(record);

    final Insert insertStatement = QueryBuilder.insertInto(table).values(fieldNames, values);

    this.executeStatement(insertStatement);
  }

  private void executeStatement(final Statement statement) {
    if (this.executeAsync) {
      this.executeAsync(statement);
    } else {
      this.execute(statement);
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
