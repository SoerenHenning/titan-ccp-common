package titan.ccp.common.avro.cassandra;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.apache.avro.specific.SpecificRecord;
import titan.ccp.common.cassandra.DataAdapter;

public class AvroDataAdapter implements DataAdapter<SpecificRecord> {

  @Override
  public List<String> getFieldNames(final SpecificRecord record) {
    return record.getSchema().getFields().stream().map(f -> f.name()).collect(Collectors.toList());
  }

  @Override
  public List<Class<?>> getFieldTypes(final SpecificRecord record) {
    final int numberOfFields = record.getSchema().getFields().size();
    return IntStream.range(0, numberOfFields).mapToObj(i -> record.get(i).getClass())
        .collect(Collectors.toList());
  }

  @Override
  public List<Object> getValues(final SpecificRecord record) {
    final int numberOfFields = record.getSchema().getFields().size();
    final List<Object> values = new ArrayList<>(numberOfFields);
    for (int i = 0; i < numberOfFields; i++) {
      values.add(record.get(i));
    }
    return values;
    // Probably less efficient:
    // return IntStream.range(0, numberOfFields).mapToObj(i ->
    // record.get(i).getClass()).collect(Collectors.toList());
  }

}
