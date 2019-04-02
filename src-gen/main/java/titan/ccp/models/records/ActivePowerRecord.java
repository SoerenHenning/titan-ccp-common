package titan.ccp.models.records;

import java.nio.BufferOverflowException;
import kieker.common.exception.RecordInstantiationException;
import kieker.common.record.AbstractMonitoringRecord;
import kieker.common.record.io.IValueDeserializer;
import kieker.common.record.io.IValueSerializer;

/**
 * @author Generic Kieker API compatibility: Kieker 1.14.0
 *
 * @since 1.14
 */
public class ActivePowerRecord extends AbstractMonitoringRecord {
  /** Descriptive definition of the serialization size of the record. */
  public static final int SIZE = TYPE_SIZE_STRING // ActivePowerRecord.identifier
      + TYPE_SIZE_LONG // ActivePowerRecord.timestamp
      + TYPE_SIZE_DOUBLE; // ActivePowerRecord.valueInW

  public static final Class<?>[] TYPES = {
      String.class, // ActivePowerRecord.identifier
      long.class, // ActivePowerRecord.timestamp
      double.class, // ActivePowerRecord.valueInW
  };

  /** default constants. */
  public static final String IDENTIFIER = "";
  private static final long serialVersionUID = 2003050765763100903L;

  /** property name array. */
  public static final String[] VALUE_NAMES = {
      "identifier",
      "timestamp",
      "valueInW",
  };

  /** property declarations. */
  private final String identifier;
  private final long timestamp;
  private final double valueInW;

  /**
   * Creates a new instance of this class using the given parameters.
   *
   * @param identifier identifier
   * @param timestamp timestamp
   * @param valueInW valueInW
   */
  public ActivePowerRecord(final String identifier, final long timestamp, final double valueInW) {
    this.identifier = identifier == null ? "" : identifier;
    this.timestamp = timestamp;
    this.valueInW = valueInW;
  }

  /**
   * This constructor converts the given array into a record. It is recommended to use the array
   * which is the result of a call to {@link #toArray()}.
   *
   * @param values The values for the record.
   *
   * @deprecated since 1.13. Use {@link #ActivePowerRecord(IValueDeserializer)} instead.
   */
  @Deprecated
  public ActivePowerRecord(final Object[] values) { // NOPMD (direct store of values)
    AbstractMonitoringRecord.checkArray(values, TYPES);
    this.identifier = (String) values[0];
    this.timestamp = (Long) values[1];
    this.valueInW = (Double) values[2];
  }

  /**
   * This constructor uses the given array to initialize the fields of this record.
   *
   * @param values The values for the record.
   * @param valueTypes The types of the elements in the first array.
   *
   * @deprecated since 1.13. Use {@link #ActivePowerRecord(IValueDeserializer)} instead.
   */
  @Deprecated
  protected ActivePowerRecord(final Object[] values, final Class<?>[] valueTypes) { // NOPMD (values
                                                                                    // stored
                                                                                    // directly)
    AbstractMonitoringRecord.checkArray(values, valueTypes);
    this.identifier = (String) values[0];
    this.timestamp = (Long) values[1];
    this.valueInW = (Double) values[2];
  }

  /**
   * @param deserializer The deserializer to use
   * @throws RecordInstantiationException when the record could not be deserialized
   */
  public ActivePowerRecord(final IValueDeserializer deserializer)
      throws RecordInstantiationException {
    this.identifier = deserializer.getString();
    this.timestamp = deserializer.getLong();
    this.valueInW = deserializer.getDouble();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void serialize(final IValueSerializer serializer) throws BufferOverflowException {
    // super.serialize(serializer);
    serializer.putString(this.getIdentifier());
    serializer.putLong(this.getTimestamp());
    serializer.putDouble(this.getValueInW());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Class<?>[] getValueTypes() {
    return TYPES; // NOPMD
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String[] getValueNames() {
    return VALUE_NAMES; // NOPMD
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int getSize() {
    return SIZE;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean equals(final Object obj) {
    if (obj == null) {
      return false;
    }
    if (obj == this) {
      return true;
    }
    if (obj.getClass() != this.getClass()) {
      return false;
    }

    final ActivePowerRecord castedRecord = (ActivePowerRecord) obj;
    if (this.getLoggingTimestamp() != castedRecord.getLoggingTimestamp()) {
      return false;
    }
    if (!this.getIdentifier().equals(castedRecord.getIdentifier())) {
      return false;
    }
    if (this.getTimestamp() != castedRecord.getTimestamp()) {
      return false;
    }
    if (isNotEqual(this.getValueInW(), castedRecord.getValueInW())) {
      return false;
    }

    return true;
  }

  public final String getIdentifier() {
    return this.identifier;
  }

  public final long getTimestamp() {
    return this.timestamp;
  }

  public final double getValueInW() {
    return this.valueInW;
  }

}
