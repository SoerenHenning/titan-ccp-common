package titan.ccp.common.cassandra;

import java.util.List;

public interface DataAdapter<T> {

  public List<String> getFieldNames(T record);

  public List<Class<?>> getFieldTypes(T record);

  public List<Object> getValues(T record);

}
