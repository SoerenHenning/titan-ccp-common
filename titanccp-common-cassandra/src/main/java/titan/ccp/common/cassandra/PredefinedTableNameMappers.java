package titan.ccp.common.cassandra;

import java.util.function.Function;

/**
 * Some functions mapping arbitrary objects to a table name.
 */
public enum PredefinedTableNameMappers implements Function<Object, String> {
  CLASS_NAME(t -> t.getClass().getName()), // source formatting
  SIMPLE_CLASS_NAME(t -> t.getClass().getSimpleName()), // source formatting
  CANONICAL_CLASS_NAME(t -> t.getClass().getCanonicalName());

  private final Function<Object, String> mapper;

  PredefinedTableNameMappers(final Function<Object, String> mapper) {
    this.mapper = mapper;

  }

  @Override
  public String apply(final Object t) {
    return this.mapper.apply(t);
  }

}
