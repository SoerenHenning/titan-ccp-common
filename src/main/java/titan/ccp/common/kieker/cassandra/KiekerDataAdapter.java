package titan.ccp.common.kieker.cassandra;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import kieker.common.record.IMonitoringRecord;
import titan.ccp.common.cassandra.DataAdapter;
import titan.ccp.common.kieker.ArrayValueSerializer;

public class KiekerDataAdapter implements DataAdapter<IMonitoringRecord> {

  private static final String RECORD_TYPE_NAME = "recordType";
  private static final Class<?> RECORD_TYPE_TYPE = String.class;

  private static final String LOGGING_TIMESTAMP_NAME = "loggingTimestamp";
  private static final Class<?> LOGGING_TIMESTAMP_TYPE = long.class;

  private final boolean includeRecordType;

  private final boolean includeLoggingTimestamp;

  public KiekerDataAdapter() {
    this(false, false);
  }

  public KiekerDataAdapter(final boolean includeRecordType, final boolean includeLoggingTimestamp) {
    this.includeRecordType = includeRecordType;
    this.includeLoggingTimestamp = includeLoggingTimestamp;
  }

  public KiekerDataAdapter includeRecordType() {
    return new KiekerDataAdapter(true, this.includeLoggingTimestamp);
  }

  public KiekerDataAdapter excludeRecordType() {
    return new KiekerDataAdapter(false, this.includeLoggingTimestamp);
  }

  public KiekerDataAdapter includeLoggingTimestamp() {
    return new KiekerDataAdapter(this.includeRecordType, true);
  }

  public KiekerDataAdapter excludeLoggingTimestamp() {
    return new KiekerDataAdapter(this.includeRecordType, false);
  }

  @Override
  public List<String> getFieldNames(final IMonitoringRecord record) {
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

  @Override
  public List<Class<?>> getFieldTypes(final IMonitoringRecord record) {
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

  @Override
  public List<Object> getValues(final IMonitoringRecord record) {
    final Object[] valueArray = new Object[record.getValueNames().length];
    record.serialize(new ArrayValueSerializer(valueArray));

    final List<Object> values = new ArrayList<>(valueArray.length + 2);
    if (this.includeRecordType) {
      values.add(record.getClass().getName());
    }
    if (this.includeLoggingTimestamp) {
      values.add(record.getLoggingTimestamp());
    }
    Collections.addAll(values, valueArray);
    return values;
  }

}
