package titan.ccp.common.kieker.cassandra;

import com.datastax.driver.core.Session;
import java.util.function.Function;
import kieker.common.record.IMonitoringRecord;
import titan.ccp.common.cassandra.CassandraWriter;
import titan.ccp.common.cassandra.PredefinedTableNameMappers;
import titan.ccp.common.cassandra.PrimaryKeySelectionStrategy;
import titan.ccp.common.cassandra.TakeLoggingTimestampStrategy;

/**
 * @deprecated Use {@link CassandraWriter} instead in conjunction with {@link KiekerDataAdapter}.
 */
@Deprecated
public class KiekerCassandraWriter {

  private final CassandraWriter<IMonitoringRecord> cassandraWriter;

  public KiekerCassandraWriter(final Session session,
      final Function<? super IMonitoringRecord, String> tableNameMapper,
      final PrimaryKeySelectionStrategy primaryKeySelectionStrategy,
      final boolean includeRecordType, final boolean includeLoggingTimestamp,
      final boolean executeAsync) {
    this.cassandraWriter = new CassandraWriter<>(session,
        new KiekerDataAdapter(includeRecordType, includeLoggingTimestamp), tableNameMapper,
        primaryKeySelectionStrategy, executeAsync);
  }

  public void write(final IMonitoringRecord record) {
    this.cassandraWriter.write(record);
  }

  public static Builder builder(final Session session) {
    return new Builder(session);
  }

  public static class Builder {

    private final Session session;

    private Function<? super IMonitoringRecord, String> tableNameMapper =
        PredefinedTableNameMappers.CLASS_NAME;

    private PrimaryKeySelectionStrategy primaryKeySelectionStrategy =
        new TakeLoggingTimestampStrategy();

    private boolean includeRecordType = false;

    private boolean includeLoggingTimestamp = true;

    private boolean executeAsync = false;

    public Builder(final Session session) {
      this.session = session;
    }

    public Builder tableNameMapper(
        final Function<? super IMonitoringRecord, String> tableNameMapper) {
      this.tableNameMapper = tableNameMapper;
      return this;
    }

    public Builder primaryKeySelectionStrategy(final PrimaryKeySelectionStrategy strategy) {
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

    public KiekerCassandraWriter build() {
      return new KiekerCassandraWriter(this.session, this.tableNameMapper,
          this.primaryKeySelectionStrategy, this.includeRecordType, this.includeLoggingTimestamp,
          this.executeAsync);
    }

  }

}
