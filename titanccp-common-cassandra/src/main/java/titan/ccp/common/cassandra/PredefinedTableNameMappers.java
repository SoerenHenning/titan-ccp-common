package titan.ccp.common.cassandra;

import java.util.function.Function;

public enum PredefinedTableNameMappers implements Function<Object, String> {
  CLASS_NAME(t -> t.getClass().getName()), SIMPLE_CLASS_NAME(
      t -> t.getClass().getSimpleName()), CANONICAL_CLASS_NAME(
          t -> t.getClass().getCanonicalName());

  private final Function<Object, String> mapper;

  private PredefinedTableNameMappers(final Function<Object, String> mapper) {
    this.mapper = mapper;

  }

  @Override
  public String apply(final Object t) {
    return this.mapper.apply(t);
  }

}
