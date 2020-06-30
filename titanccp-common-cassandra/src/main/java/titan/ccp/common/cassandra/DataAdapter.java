package titan.ccp.common.cassandra;

import java.util.List;

/**
 * Adapter interface providing methods for making a record type writable by a
 * {@link CassandraWriter}.
 *
 * @param <T> Type of record to be written
 */
public interface DataAdapter<T> {

  List<String> getFieldNames(T record);

  List<Class<?>> getFieldTypes(T record);

  List<Object> getValues(T record);

}
