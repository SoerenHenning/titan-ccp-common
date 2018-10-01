package titan.ccp.common.kieker.cassandra;

import java.util.function.Function;
import kieker.common.record.IMonitoringRecord;

public enum PredefinedTableNameMappers implements Function<IMonitoringRecord, String> {
  CLASS_NAME(t -> t.getClass().getName()), SIMPLE_CLASS_NAME(
      t -> t.getClass().getSimpleName()), CANONICAL_CLASS_NAME(
          t -> t.getClass().getCanonicalName());

  private final Function<IMonitoringRecord, String> mapper;

  private PredefinedTableNameMappers(final Function<IMonitoringRecord, String> mapper) {
    this.mapper = mapper;

  }

  @Override
  public String apply(final IMonitoringRecord t) {
    return this.mapper.apply(t);
  }

}
