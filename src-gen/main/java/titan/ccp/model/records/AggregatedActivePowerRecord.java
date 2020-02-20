/**
 * Autogenerated by Avro
 *
 * DO NOT EDIT DIRECTLY
 */
package titan.ccp.model.records;

import org.apache.avro.generic.GenericArray;
import org.apache.avro.specific.SpecificData;
import org.apache.avro.util.Utf8;
import org.apache.avro.message.BinaryMessageEncoder;
import org.apache.avro.message.BinaryMessageDecoder;
import org.apache.avro.message.SchemaStore;

@org.apache.avro.specific.AvroGenerated
public class AggregatedActivePowerRecord extends org.apache.avro.specific.SpecificRecordBase implements org.apache.avro.specific.SpecificRecord {
  private static final long serialVersionUID = 7835087803663896279L;
  public static final org.apache.avro.Schema SCHEMA$ = new org.apache.avro.Schema.Parser().parse("{\"type\":\"record\",\"name\":\"AggregatedActivePowerRecord\",\"namespace\":\"titan.ccp.model.records\",\"fields\":[{\"name\":\"identifier\",\"type\":{\"type\":\"string\",\"avro.java.string\":\"String\"},\"doc\":\"*\"},{\"name\":\"timestamp\",\"type\":\"long\",\"doc\":\"*\"},{\"name\":\"count\",\"type\":\"long\",\"doc\":\"*\"},{\"name\":\"sumInW\",\"type\":\"double\",\"doc\":\"*\"},{\"name\":\"averageInW\",\"type\":\"double\",\"doc\":\"*\"}]}");
  public static org.apache.avro.Schema getClassSchema() { return SCHEMA$; }

  private static SpecificData MODEL$ = new SpecificData();

  private static final BinaryMessageEncoder<AggregatedActivePowerRecord> ENCODER =
      new BinaryMessageEncoder<AggregatedActivePowerRecord>(MODEL$, SCHEMA$);

  private static final BinaryMessageDecoder<AggregatedActivePowerRecord> DECODER =
      new BinaryMessageDecoder<AggregatedActivePowerRecord>(MODEL$, SCHEMA$);

  /**
   * Return the BinaryMessageEncoder instance used by this class.
   * @return the message encoder used by this class
   */
  public static BinaryMessageEncoder<AggregatedActivePowerRecord> getEncoder() {
    return ENCODER;
  }

  /**
   * Return the BinaryMessageDecoder instance used by this class.
   * @return the message decoder used by this class
   */
  public static BinaryMessageDecoder<AggregatedActivePowerRecord> getDecoder() {
    return DECODER;
  }

  /**
   * Create a new BinaryMessageDecoder instance for this class that uses the specified {@link SchemaStore}.
   * @param resolver a {@link SchemaStore} used to find schemas by fingerprint
   * @return a BinaryMessageDecoder instance for this class backed by the given SchemaStore
   */
  public static BinaryMessageDecoder<AggregatedActivePowerRecord> createDecoder(SchemaStore resolver) {
    return new BinaryMessageDecoder<AggregatedActivePowerRecord>(MODEL$, SCHEMA$, resolver);
  }

  /**
   * Serializes this AggregatedActivePowerRecord to a ByteBuffer.
   * @return a buffer holding the serialized data for this instance
   * @throws java.io.IOException if this instance could not be serialized
   */
  public java.nio.ByteBuffer toByteBuffer() throws java.io.IOException {
    return ENCODER.encode(this);
  }

  /**
   * Deserializes a AggregatedActivePowerRecord from a ByteBuffer.
   * @param b a byte buffer holding serialized data for an instance of this class
   * @return a AggregatedActivePowerRecord instance decoded from the given buffer
   * @throws java.io.IOException if the given bytes could not be deserialized into an instance of this class
   */
  public static AggregatedActivePowerRecord fromByteBuffer(
      java.nio.ByteBuffer b) throws java.io.IOException {
    return DECODER.decode(b);
  }

  /** * */
   private java.lang.String identifier;
  /** * */
   private long timestamp;
  /** * */
   private long count;
  /** * */
   private double sumInW;
  /** * */
   private double averageInW;

  /**
   * Default constructor.  Note that this does not initialize fields
   * to their default values from the schema.  If that is desired then
   * one should use <code>newBuilder()</code>.
   */
  public AggregatedActivePowerRecord() {}

  /**
   * All-args constructor.
   * @param identifier *
   * @param timestamp *
   * @param count *
   * @param sumInW *
   * @param averageInW *
   */
  public AggregatedActivePowerRecord(java.lang.String identifier, java.lang.Long timestamp, java.lang.Long count, java.lang.Double sumInW, java.lang.Double averageInW) {
    this.identifier = identifier;
    this.timestamp = timestamp;
    this.count = count;
    this.sumInW = sumInW;
    this.averageInW = averageInW;
  }

  public org.apache.avro.specific.SpecificData getSpecificData() { return MODEL$; }
  public org.apache.avro.Schema getSchema() { return SCHEMA$; }
  // Used by DatumWriter.  Applications should not call.
  public java.lang.Object get(int field$) {
    switch (field$) {
    case 0: return identifier;
    case 1: return timestamp;
    case 2: return count;
    case 3: return sumInW;
    case 4: return averageInW;
    default: throw new org.apache.avro.AvroRuntimeException("Bad index");
    }
  }

  // Used by DatumReader.  Applications should not call.
  @SuppressWarnings(value="unchecked")
  public void put(int field$, java.lang.Object value$) {
    switch (field$) {
    case 0: identifier = (java.lang.String)value$; break;
    case 1: timestamp = (java.lang.Long)value$; break;
    case 2: count = (java.lang.Long)value$; break;
    case 3: sumInW = (java.lang.Double)value$; break;
    case 4: averageInW = (java.lang.Double)value$; break;
    default: throw new org.apache.avro.AvroRuntimeException("Bad index");
    }
  }

  /**
   * Gets the value of the 'identifier' field.
   * @return *
   */
  public java.lang.String getIdentifier() {
    return identifier;
  }



  /**
   * Gets the value of the 'timestamp' field.
   * @return *
   */
  public long getTimestamp() {
    return timestamp;
  }



  /**
   * Gets the value of the 'count' field.
   * @return *
   */
  public long getCount() {
    return count;
  }



  /**
   * Gets the value of the 'sumInW' field.
   * @return *
   */
  public double getSumInW() {
    return sumInW;
  }



  /**
   * Gets the value of the 'averageInW' field.
   * @return *
   */
  public double getAverageInW() {
    return averageInW;
  }



  /**
   * Creates a new AggregatedActivePowerRecord RecordBuilder.
   * @return A new AggregatedActivePowerRecord RecordBuilder
   */
  public static titan.ccp.model.records.AggregatedActivePowerRecord.Builder newBuilder() {
    return new titan.ccp.model.records.AggregatedActivePowerRecord.Builder();
  }

  /**
   * Creates a new AggregatedActivePowerRecord RecordBuilder by copying an existing Builder.
   * @param other The existing builder to copy.
   * @return A new AggregatedActivePowerRecord RecordBuilder
   */
  public static titan.ccp.model.records.AggregatedActivePowerRecord.Builder newBuilder(titan.ccp.model.records.AggregatedActivePowerRecord.Builder other) {
    if (other == null) {
      return new titan.ccp.model.records.AggregatedActivePowerRecord.Builder();
    } else {
      return new titan.ccp.model.records.AggregatedActivePowerRecord.Builder(other);
    }
  }

  /**
   * Creates a new AggregatedActivePowerRecord RecordBuilder by copying an existing AggregatedActivePowerRecord instance.
   * @param other The existing instance to copy.
   * @return A new AggregatedActivePowerRecord RecordBuilder
   */
  public static titan.ccp.model.records.AggregatedActivePowerRecord.Builder newBuilder(titan.ccp.model.records.AggregatedActivePowerRecord other) {
    if (other == null) {
      return new titan.ccp.model.records.AggregatedActivePowerRecord.Builder();
    } else {
      return new titan.ccp.model.records.AggregatedActivePowerRecord.Builder(other);
    }
  }

  /**
   * RecordBuilder for AggregatedActivePowerRecord instances.
   */
  public static class Builder extends org.apache.avro.specific.SpecificRecordBuilderBase<AggregatedActivePowerRecord>
    implements org.apache.avro.data.RecordBuilder<AggregatedActivePowerRecord> {

    /** * */
    private java.lang.String identifier;
    /** * */
    private long timestamp;
    /** * */
    private long count;
    /** * */
    private double sumInW;
    /** * */
    private double averageInW;

    /** Creates a new Builder */
    private Builder() {
      super(SCHEMA$);
    }

    /**
     * Creates a Builder by copying an existing Builder.
     * @param other The existing Builder to copy.
     */
    private Builder(titan.ccp.model.records.AggregatedActivePowerRecord.Builder other) {
      super(other);
      if (isValidValue(fields()[0], other.identifier)) {
        this.identifier = data().deepCopy(fields()[0].schema(), other.identifier);
        fieldSetFlags()[0] = other.fieldSetFlags()[0];
      }
      if (isValidValue(fields()[1], other.timestamp)) {
        this.timestamp = data().deepCopy(fields()[1].schema(), other.timestamp);
        fieldSetFlags()[1] = other.fieldSetFlags()[1];
      }
      if (isValidValue(fields()[2], other.count)) {
        this.count = data().deepCopy(fields()[2].schema(), other.count);
        fieldSetFlags()[2] = other.fieldSetFlags()[2];
      }
      if (isValidValue(fields()[3], other.sumInW)) {
        this.sumInW = data().deepCopy(fields()[3].schema(), other.sumInW);
        fieldSetFlags()[3] = other.fieldSetFlags()[3];
      }
      if (isValidValue(fields()[4], other.averageInW)) {
        this.averageInW = data().deepCopy(fields()[4].schema(), other.averageInW);
        fieldSetFlags()[4] = other.fieldSetFlags()[4];
      }
    }

    /**
     * Creates a Builder by copying an existing AggregatedActivePowerRecord instance
     * @param other The existing instance to copy.
     */
    private Builder(titan.ccp.model.records.AggregatedActivePowerRecord other) {
      super(SCHEMA$);
      if (isValidValue(fields()[0], other.identifier)) {
        this.identifier = data().deepCopy(fields()[0].schema(), other.identifier);
        fieldSetFlags()[0] = true;
      }
      if (isValidValue(fields()[1], other.timestamp)) {
        this.timestamp = data().deepCopy(fields()[1].schema(), other.timestamp);
        fieldSetFlags()[1] = true;
      }
      if (isValidValue(fields()[2], other.count)) {
        this.count = data().deepCopy(fields()[2].schema(), other.count);
        fieldSetFlags()[2] = true;
      }
      if (isValidValue(fields()[3], other.sumInW)) {
        this.sumInW = data().deepCopy(fields()[3].schema(), other.sumInW);
        fieldSetFlags()[3] = true;
      }
      if (isValidValue(fields()[4], other.averageInW)) {
        this.averageInW = data().deepCopy(fields()[4].schema(), other.averageInW);
        fieldSetFlags()[4] = true;
      }
    }

    /**
      * Gets the value of the 'identifier' field.
      * *
      * @return The value.
      */
    public java.lang.String getIdentifier() {
      return identifier;
    }


    /**
      * Sets the value of the 'identifier' field.
      * *
      * @param value The value of 'identifier'.
      * @return This builder.
      */
    public titan.ccp.model.records.AggregatedActivePowerRecord.Builder setIdentifier(java.lang.String value) {
      validate(fields()[0], value);
      this.identifier = value;
      fieldSetFlags()[0] = true;
      return this;
    }

    /**
      * Checks whether the 'identifier' field has been set.
      * *
      * @return True if the 'identifier' field has been set, false otherwise.
      */
    public boolean hasIdentifier() {
      return fieldSetFlags()[0];
    }


    /**
      * Clears the value of the 'identifier' field.
      * *
      * @return This builder.
      */
    public titan.ccp.model.records.AggregatedActivePowerRecord.Builder clearIdentifier() {
      identifier = null;
      fieldSetFlags()[0] = false;
      return this;
    }

    /**
      * Gets the value of the 'timestamp' field.
      * *
      * @return The value.
      */
    public long getTimestamp() {
      return timestamp;
    }


    /**
      * Sets the value of the 'timestamp' field.
      * *
      * @param value The value of 'timestamp'.
      * @return This builder.
      */
    public titan.ccp.model.records.AggregatedActivePowerRecord.Builder setTimestamp(long value) {
      validate(fields()[1], value);
      this.timestamp = value;
      fieldSetFlags()[1] = true;
      return this;
    }

    /**
      * Checks whether the 'timestamp' field has been set.
      * *
      * @return True if the 'timestamp' field has been set, false otherwise.
      */
    public boolean hasTimestamp() {
      return fieldSetFlags()[1];
    }


    /**
      * Clears the value of the 'timestamp' field.
      * *
      * @return This builder.
      */
    public titan.ccp.model.records.AggregatedActivePowerRecord.Builder clearTimestamp() {
      fieldSetFlags()[1] = false;
      return this;
    }

    /**
      * Gets the value of the 'count' field.
      * *
      * @return The value.
      */
    public long getCount() {
      return count;
    }


    /**
      * Sets the value of the 'count' field.
      * *
      * @param value The value of 'count'.
      * @return This builder.
      */
    public titan.ccp.model.records.AggregatedActivePowerRecord.Builder setCount(long value) {
      validate(fields()[2], value);
      this.count = value;
      fieldSetFlags()[2] = true;
      return this;
    }

    /**
      * Checks whether the 'count' field has been set.
      * *
      * @return True if the 'count' field has been set, false otherwise.
      */
    public boolean hasCount() {
      return fieldSetFlags()[2];
    }


    /**
      * Clears the value of the 'count' field.
      * *
      * @return This builder.
      */
    public titan.ccp.model.records.AggregatedActivePowerRecord.Builder clearCount() {
      fieldSetFlags()[2] = false;
      return this;
    }

    /**
      * Gets the value of the 'sumInW' field.
      * *
      * @return The value.
      */
    public double getSumInW() {
      return sumInW;
    }


    /**
      * Sets the value of the 'sumInW' field.
      * *
      * @param value The value of 'sumInW'.
      * @return This builder.
      */
    public titan.ccp.model.records.AggregatedActivePowerRecord.Builder setSumInW(double value) {
      validate(fields()[3], value);
      this.sumInW = value;
      fieldSetFlags()[3] = true;
      return this;
    }

    /**
      * Checks whether the 'sumInW' field has been set.
      * *
      * @return True if the 'sumInW' field has been set, false otherwise.
      */
    public boolean hasSumInW() {
      return fieldSetFlags()[3];
    }


    /**
      * Clears the value of the 'sumInW' field.
      * *
      * @return This builder.
      */
    public titan.ccp.model.records.AggregatedActivePowerRecord.Builder clearSumInW() {
      fieldSetFlags()[3] = false;
      return this;
    }

    /**
      * Gets the value of the 'averageInW' field.
      * *
      * @return The value.
      */
    public double getAverageInW() {
      return averageInW;
    }


    /**
      * Sets the value of the 'averageInW' field.
      * *
      * @param value The value of 'averageInW'.
      * @return This builder.
      */
    public titan.ccp.model.records.AggregatedActivePowerRecord.Builder setAverageInW(double value) {
      validate(fields()[4], value);
      this.averageInW = value;
      fieldSetFlags()[4] = true;
      return this;
    }

    /**
      * Checks whether the 'averageInW' field has been set.
      * *
      * @return True if the 'averageInW' field has been set, false otherwise.
      */
    public boolean hasAverageInW() {
      return fieldSetFlags()[4];
    }


    /**
      * Clears the value of the 'averageInW' field.
      * *
      * @return This builder.
      */
    public titan.ccp.model.records.AggregatedActivePowerRecord.Builder clearAverageInW() {
      fieldSetFlags()[4] = false;
      return this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public AggregatedActivePowerRecord build() {
      try {
        AggregatedActivePowerRecord record = new AggregatedActivePowerRecord();
        record.identifier = fieldSetFlags()[0] ? this.identifier : (java.lang.String) defaultValue(fields()[0]);
        record.timestamp = fieldSetFlags()[1] ? this.timestamp : (java.lang.Long) defaultValue(fields()[1]);
        record.count = fieldSetFlags()[2] ? this.count : (java.lang.Long) defaultValue(fields()[2]);
        record.sumInW = fieldSetFlags()[3] ? this.sumInW : (java.lang.Double) defaultValue(fields()[3]);
        record.averageInW = fieldSetFlags()[4] ? this.averageInW : (java.lang.Double) defaultValue(fields()[4]);
        return record;
      } catch (org.apache.avro.AvroMissingFieldException e) {
        throw e;
      } catch (java.lang.Exception e) {
        throw new org.apache.avro.AvroRuntimeException(e);
      }
    }
  }

  @SuppressWarnings("unchecked")
  private static final org.apache.avro.io.DatumWriter<AggregatedActivePowerRecord>
    WRITER$ = (org.apache.avro.io.DatumWriter<AggregatedActivePowerRecord>)MODEL$.createDatumWriter(SCHEMA$);

  @Override public void writeExternal(java.io.ObjectOutput out)
    throws java.io.IOException {
    WRITER$.write(this, SpecificData.getEncoder(out));
  }

  @SuppressWarnings("unchecked")
  private static final org.apache.avro.io.DatumReader<AggregatedActivePowerRecord>
    READER$ = (org.apache.avro.io.DatumReader<AggregatedActivePowerRecord>)MODEL$.createDatumReader(SCHEMA$);

  @Override public void readExternal(java.io.ObjectInput in)
    throws java.io.IOException {
    READER$.read(this, SpecificData.getDecoder(in));
  }

  @Override protected boolean hasCustomCoders() { return true; }

  @Override public void customEncode(org.apache.avro.io.Encoder out)
    throws java.io.IOException
  {
    out.writeString(this.identifier);

    out.writeLong(this.timestamp);

    out.writeLong(this.count);

    out.writeDouble(this.sumInW);

    out.writeDouble(this.averageInW);

  }

  @Override public void customDecode(org.apache.avro.io.ResolvingDecoder in)
    throws java.io.IOException
  {
    org.apache.avro.Schema.Field[] fieldOrder = in.readFieldOrderIfDiff();
    if (fieldOrder == null) {
      this.identifier = in.readString();

      this.timestamp = in.readLong();

      this.count = in.readLong();

      this.sumInW = in.readDouble();

      this.averageInW = in.readDouble();

    } else {
      for (int i = 0; i < 5; i++) {
        switch (fieldOrder[i].pos()) {
        case 0:
          this.identifier = in.readString();
          break;

        case 1:
          this.timestamp = in.readLong();
          break;

        case 2:
          this.count = in.readLong();
          break;

        case 3:
          this.sumInW = in.readDouble();
          break;

        case 4:
          this.averageInW = in.readDouble();
          break;

        default:
          throw new java.io.IOException("Corrupt ResolvingDecoder.");
        }
      }
    }
  }
}










