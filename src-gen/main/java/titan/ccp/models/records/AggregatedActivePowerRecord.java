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
public class AggregatedActivePowerRecord extends AbstractMonitoringRecord {
  /** Descriptive definition of the serialization size of the record. */
  public static final int SIZE = TYPE_SIZE_STRING // AggregatedActivePowerRecord.identifier
      + TYPE_SIZE_LONG // AggregatedActivePowerRecord.timestamp
      + TYPE_SIZE_DOUBLE // AggregatedActivePowerRecord.minInW
      + TYPE_SIZE_DOUBLE // AggregatedActivePowerRecord.maxInW
      + TYPE_SIZE_LONG // AggregatedActivePowerRecord.count
      + TYPE_SIZE_DOUBLE // AggregatedActivePowerRecord.sumInW
      + TYPE_SIZE_DOUBLE; // AggregatedActivePowerRecord.averageInW

  public static final Class<?>[] TYPES = {
      String.class, // AggregatedActivePowerRecord.identifier
      long.class, // AggregatedActivePowerRecord.timestamp
      double.class, // AggregatedActivePowerRecord.minInW
      double.class, // AggregatedActivePowerRecord.maxInW
      long.class, // AggregatedActivePowerRecord.count
      double.class, // AggregatedActivePowerRecord.sumInW
      double.class, // AggregatedActivePowerRecord.averageInW
  };

  /** default constants. */
  public static final String IDENTIFIER = "";
  private static final long serialVersionUID = -2095448023003244219L;

  /** property name array. */
  public static final String[] VALUE_NAMES = {
      "identifier",
      "timestamp",
      "minInW",
      "maxInW",
      "count",
      "sumInW",
      "averageInW",
  };

  /** property declarations. */
  private final String identifier;
  private final long timestamp;
  private final double minInW;
  private final double maxInW;
  private final long count;
  private final double sumInW;
  private final double averageInW;

  /**
   * Creates a new instance of this class using the given parameters.
   *
   * @param identifier identifier
   * @param timestamp timestamp
   * @param minInW minInW
   * @param maxInW maxInW
   * @param count count
   * @param sumInW sumInW
   * @param averageInW averageInW
   */
  public AggregatedActivePowerRecord(final String identifier, final long timestamp,
      final double minInW, final double maxInW, final long count,
      final double sumInW, final double averageInW) {
    this.identifier = identifier == null ? "" : identifier;
    this.timestamp = timestamp;
    this.minInW = minInW;
    this.maxInW = maxInW;
    this.count = count;
    this.sumInW = sumInW;
    this.averageInW = averageInW;
  }

  /**
   * @param deserializer The deserializer to use
   * @throws RecordInstantiationException when the record could not be deserialized
   */
  public AggregatedActivePowerRecord(final IValueDeserializer deserializer)
      throws RecordInstantiationException {
    this.identifier = deserializer.getString();
    this.timestamp = deserializer.getLong();
    this.minInW = deserializer.getDouble();
    this.maxInW = deserializer.getDouble();
    this.count = deserializer.getLong();
    this.sumInW = deserializer.getDouble();
    this.averageInW = deserializer.getDouble();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void serialize(final IValueSerializer serializer) throws BufferOverflowException {
    // super.serialize(serializer);
    serializer.putString(this.getIdentifier());
    serializer.putLong(this.getTimestamp());
    serializer.putDouble(this.getMinInW());
    serializer.putDouble(this.getMaxInW());
    serializer.putLong(this.getCount());
    serializer.putDouble(this.getSumInW());
    serializer.putDouble(this.getAverageInW());
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

    final AggregatedActivePowerRecord castedRecord = (AggregatedActivePowerRecord) obj;
    if (this.getLoggingTimestamp() != castedRecord.getLoggingTimestamp()) {
      return false;
    }
    if (!this.getIdentifier().equals(castedRecord.getIdentifier())) {
      return false;
    }
    if (this.getTimestamp() != castedRecord.getTimestamp()) {
      return false;
    }
    if (isNotEqual(this.getMinInW(), castedRecord.getMinInW())) {
      return false;
    }
    if (isNotEqual(this.getMaxInW(), castedRecord.getMaxInW())) {
      return false;
    }
    if (this.getCount() != castedRecord.getCount()) {
      return false;
    }
    if (isNotEqual(this.getSumInW(), castedRecord.getSumInW())) {
      return false;
    }
    if (isNotEqual(this.getAverageInW(), castedRecord.getAverageInW())) {
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

  public final double getMinInW() {
    return this.minInW;
  }

  public final double getMaxInW() {
    return this.maxInW;
  }

  public final long getCount() {
    return this.count;
  }

  public final double getSumInW() {
    return this.sumInW;
  }

  public final double getAverageInW() {
    return this.averageInW;
  }

}
