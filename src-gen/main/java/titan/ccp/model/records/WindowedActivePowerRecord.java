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
public class WindowedActivePowerRecord extends org.apache.avro.specific.SpecificRecordBase implements org.apache.avro.specific.SpecificRecord {
  private static final long serialVersionUID = 4901468746138541707L;
  public static final org.apache.avro.Schema SCHEMA$ = new org.apache.avro.Schema.Parser().parse("{\"type\":\"record\",\"name\":\"WindowedActivePowerRecord\",\"namespace\":\"titan.ccp.model.records\",\"fields\":[{\"name\":\"identifier\",\"type\":{\"type\":\"string\",\"avro.java.string\":\"String\"}},{\"name\":\"startTimestamp\",\"type\":\"long\"},{\"name\":\"endTimestamp\",\"type\":\"long\"},{\"name\":\"count\",\"type\":\"long\"},{\"name\":\"mean\",\"type\":\"double\"},{\"name\":\"populationVariance\",\"type\":\"double\"},{\"name\":\"min\",\"type\":\"double\"},{\"name\":\"max\",\"type\":\"double\"}]}");
  public static org.apache.avro.Schema getClassSchema() { return SCHEMA$; }

  private static SpecificData MODEL$ = new SpecificData();

  private static final BinaryMessageEncoder<WindowedActivePowerRecord> ENCODER =
      new BinaryMessageEncoder<WindowedActivePowerRecord>(MODEL$, SCHEMA$);

  private static final BinaryMessageDecoder<WindowedActivePowerRecord> DECODER =
      new BinaryMessageDecoder<WindowedActivePowerRecord>(MODEL$, SCHEMA$);

  /**
   * Return the BinaryMessageEncoder instance used by this class.
   * @return the message encoder used by this class
   */
  public static BinaryMessageEncoder<WindowedActivePowerRecord> getEncoder() {
    return ENCODER;
  }

  /**
   * Return the BinaryMessageDecoder instance used by this class.
   * @return the message decoder used by this class
   */
  public static BinaryMessageDecoder<WindowedActivePowerRecord> getDecoder() {
    return DECODER;
  }

  /**
   * Create a new BinaryMessageDecoder instance for this class that uses the specified {@link SchemaStore}.
   * @param resolver a {@link SchemaStore} used to find schemas by fingerprint
   * @return a BinaryMessageDecoder instance for this class backed by the given SchemaStore
   */
  public static BinaryMessageDecoder<WindowedActivePowerRecord> createDecoder(SchemaStore resolver) {
    return new BinaryMessageDecoder<WindowedActivePowerRecord>(MODEL$, SCHEMA$, resolver);
  }

  /**
   * Serializes this WindowedActivePowerRecord to a ByteBuffer.
   * @return a buffer holding the serialized data for this instance
   * @throws java.io.IOException if this instance could not be serialized
   */
  public java.nio.ByteBuffer toByteBuffer() throws java.io.IOException {
    return ENCODER.encode(this);
  }

  /**
   * Deserializes a WindowedActivePowerRecord from a ByteBuffer.
   * @param b a byte buffer holding serialized data for an instance of this class
   * @return a WindowedActivePowerRecord instance decoded from the given buffer
   * @throws java.io.IOException if the given bytes could not be deserialized into an instance of this class
   */
  public static WindowedActivePowerRecord fromByteBuffer(
      java.nio.ByteBuffer b) throws java.io.IOException {
    return DECODER.decode(b);
  }

   private java.lang.String identifier;
   private long startTimestamp;
   private long endTimestamp;
   private long count;
   private double mean;
   private double populationVariance;
   private double min;
   private double max;

  /**
   * Default constructor.  Note that this does not initialize fields
   * to their default values from the schema.  If that is desired then
   * one should use <code>newBuilder()</code>.
   */
  public WindowedActivePowerRecord() {}

  /**
   * All-args constructor.
   * @param identifier The new value for identifier
   * @param startTimestamp The new value for startTimestamp
   * @param endTimestamp The new value for endTimestamp
   * @param count The new value for count
   * @param mean The new value for mean
   * @param populationVariance The new value for populationVariance
   * @param min The new value for min
   * @param max The new value for max
   */
  public WindowedActivePowerRecord(java.lang.String identifier, java.lang.Long startTimestamp, java.lang.Long endTimestamp, java.lang.Long count, java.lang.Double mean, java.lang.Double populationVariance, java.lang.Double min, java.lang.Double max) {
    this.identifier = identifier;
    this.startTimestamp = startTimestamp;
    this.endTimestamp = endTimestamp;
    this.count = count;
    this.mean = mean;
    this.populationVariance = populationVariance;
    this.min = min;
    this.max = max;
  }

  public org.apache.avro.specific.SpecificData getSpecificData() { return MODEL$; }
  public org.apache.avro.Schema getSchema() { return SCHEMA$; }
  // Used by DatumWriter.  Applications should not call.
  public java.lang.Object get(int field$) {
    switch (field$) {
    case 0: return identifier;
    case 1: return startTimestamp;
    case 2: return endTimestamp;
    case 3: return count;
    case 4: return mean;
    case 5: return populationVariance;
    case 6: return min;
    case 7: return max;
    default: throw new org.apache.avro.AvroRuntimeException("Bad index");
    }
  }

  // Used by DatumReader.  Applications should not call.
  @SuppressWarnings(value="unchecked")
  public void put(int field$, java.lang.Object value$) {
    switch (field$) {
    case 0: identifier = (java.lang.String)value$; break;
    case 1: startTimestamp = (java.lang.Long)value$; break;
    case 2: endTimestamp = (java.lang.Long)value$; break;
    case 3: count = (java.lang.Long)value$; break;
    case 4: mean = (java.lang.Double)value$; break;
    case 5: populationVariance = (java.lang.Double)value$; break;
    case 6: min = (java.lang.Double)value$; break;
    case 7: max = (java.lang.Double)value$; break;
    default: throw new org.apache.avro.AvroRuntimeException("Bad index");
    }
  }

  /**
   * Gets the value of the 'identifier' field.
   * @return The value of the 'identifier' field.
   */
  public java.lang.String getIdentifier() {
    return identifier;
  }



  /**
   * Gets the value of the 'startTimestamp' field.
   * @return The value of the 'startTimestamp' field.
   */
  public long getStartTimestamp() {
    return startTimestamp;
  }



  /**
   * Gets the value of the 'endTimestamp' field.
   * @return The value of the 'endTimestamp' field.
   */
  public long getEndTimestamp() {
    return endTimestamp;
  }



  /**
   * Gets the value of the 'count' field.
   * @return The value of the 'count' field.
   */
  public long getCount() {
    return count;
  }



  /**
   * Gets the value of the 'mean' field.
   * @return The value of the 'mean' field.
   */
  public double getMean() {
    return mean;
  }



  /**
   * Gets the value of the 'populationVariance' field.
   * @return The value of the 'populationVariance' field.
   */
  public double getPopulationVariance() {
    return populationVariance;
  }



  /**
   * Gets the value of the 'min' field.
   * @return The value of the 'min' field.
   */
  public double getMin() {
    return min;
  }



  /**
   * Gets the value of the 'max' field.
   * @return The value of the 'max' field.
   */
  public double getMax() {
    return max;
  }



  /**
   * Creates a new WindowedActivePowerRecord RecordBuilder.
   * @return A new WindowedActivePowerRecord RecordBuilder
   */
  public static titan.ccp.model.records.WindowedActivePowerRecord.Builder newBuilder() {
    return new titan.ccp.model.records.WindowedActivePowerRecord.Builder();
  }

  /**
   * Creates a new WindowedActivePowerRecord RecordBuilder by copying an existing Builder.
   * @param other The existing builder to copy.
   * @return A new WindowedActivePowerRecord RecordBuilder
   */
  public static titan.ccp.model.records.WindowedActivePowerRecord.Builder newBuilder(titan.ccp.model.records.WindowedActivePowerRecord.Builder other) {
    if (other == null) {
      return new titan.ccp.model.records.WindowedActivePowerRecord.Builder();
    } else {
      return new titan.ccp.model.records.WindowedActivePowerRecord.Builder(other);
    }
  }

  /**
   * Creates a new WindowedActivePowerRecord RecordBuilder by copying an existing WindowedActivePowerRecord instance.
   * @param other The existing instance to copy.
   * @return A new WindowedActivePowerRecord RecordBuilder
   */
  public static titan.ccp.model.records.WindowedActivePowerRecord.Builder newBuilder(titan.ccp.model.records.WindowedActivePowerRecord other) {
    if (other == null) {
      return new titan.ccp.model.records.WindowedActivePowerRecord.Builder();
    } else {
      return new titan.ccp.model.records.WindowedActivePowerRecord.Builder(other);
    }
  }

  /**
   * RecordBuilder for WindowedActivePowerRecord instances.
   */
  public static class Builder extends org.apache.avro.specific.SpecificRecordBuilderBase<WindowedActivePowerRecord>
    implements org.apache.avro.data.RecordBuilder<WindowedActivePowerRecord> {

    private java.lang.String identifier;
    private long startTimestamp;
    private long endTimestamp;
    private long count;
    private double mean;
    private double populationVariance;
    private double min;
    private double max;

    /** Creates a new Builder */
    private Builder() {
      super(SCHEMA$);
    }

    /**
     * Creates a Builder by copying an existing Builder.
     * @param other The existing Builder to copy.
     */
    private Builder(titan.ccp.model.records.WindowedActivePowerRecord.Builder other) {
      super(other);
      if (isValidValue(fields()[0], other.identifier)) {
        this.identifier = data().deepCopy(fields()[0].schema(), other.identifier);
        fieldSetFlags()[0] = other.fieldSetFlags()[0];
      }
      if (isValidValue(fields()[1], other.startTimestamp)) {
        this.startTimestamp = data().deepCopy(fields()[1].schema(), other.startTimestamp);
        fieldSetFlags()[1] = other.fieldSetFlags()[1];
      }
      if (isValidValue(fields()[2], other.endTimestamp)) {
        this.endTimestamp = data().deepCopy(fields()[2].schema(), other.endTimestamp);
        fieldSetFlags()[2] = other.fieldSetFlags()[2];
      }
      if (isValidValue(fields()[3], other.count)) {
        this.count = data().deepCopy(fields()[3].schema(), other.count);
        fieldSetFlags()[3] = other.fieldSetFlags()[3];
      }
      if (isValidValue(fields()[4], other.mean)) {
        this.mean = data().deepCopy(fields()[4].schema(), other.mean);
        fieldSetFlags()[4] = other.fieldSetFlags()[4];
      }
      if (isValidValue(fields()[5], other.populationVariance)) {
        this.populationVariance = data().deepCopy(fields()[5].schema(), other.populationVariance);
        fieldSetFlags()[5] = other.fieldSetFlags()[5];
      }
      if (isValidValue(fields()[6], other.min)) {
        this.min = data().deepCopy(fields()[6].schema(), other.min);
        fieldSetFlags()[6] = other.fieldSetFlags()[6];
      }
      if (isValidValue(fields()[7], other.max)) {
        this.max = data().deepCopy(fields()[7].schema(), other.max);
        fieldSetFlags()[7] = other.fieldSetFlags()[7];
      }
    }

    /**
     * Creates a Builder by copying an existing WindowedActivePowerRecord instance
     * @param other The existing instance to copy.
     */
    private Builder(titan.ccp.model.records.WindowedActivePowerRecord other) {
      super(SCHEMA$);
      if (isValidValue(fields()[0], other.identifier)) {
        this.identifier = data().deepCopy(fields()[0].schema(), other.identifier);
        fieldSetFlags()[0] = true;
      }
      if (isValidValue(fields()[1], other.startTimestamp)) {
        this.startTimestamp = data().deepCopy(fields()[1].schema(), other.startTimestamp);
        fieldSetFlags()[1] = true;
      }
      if (isValidValue(fields()[2], other.endTimestamp)) {
        this.endTimestamp = data().deepCopy(fields()[2].schema(), other.endTimestamp);
        fieldSetFlags()[2] = true;
      }
      if (isValidValue(fields()[3], other.count)) {
        this.count = data().deepCopy(fields()[3].schema(), other.count);
        fieldSetFlags()[3] = true;
      }
      if (isValidValue(fields()[4], other.mean)) {
        this.mean = data().deepCopy(fields()[4].schema(), other.mean);
        fieldSetFlags()[4] = true;
      }
      if (isValidValue(fields()[5], other.populationVariance)) {
        this.populationVariance = data().deepCopy(fields()[5].schema(), other.populationVariance);
        fieldSetFlags()[5] = true;
      }
      if (isValidValue(fields()[6], other.min)) {
        this.min = data().deepCopy(fields()[6].schema(), other.min);
        fieldSetFlags()[6] = true;
      }
      if (isValidValue(fields()[7], other.max)) {
        this.max = data().deepCopy(fields()[7].schema(), other.max);
        fieldSetFlags()[7] = true;
      }
    }

    /**
      * Gets the value of the 'identifier' field.
      * @return The value.
      */
    public java.lang.String getIdentifier() {
      return identifier;
    }


    /**
      * Sets the value of the 'identifier' field.
      * @param value The value of 'identifier'.
      * @return This builder.
      */
    public titan.ccp.model.records.WindowedActivePowerRecord.Builder setIdentifier(java.lang.String value) {
      validate(fields()[0], value);
      this.identifier = value;
      fieldSetFlags()[0] = true;
      return this;
    }

    /**
      * Checks whether the 'identifier' field has been set.
      * @return True if the 'identifier' field has been set, false otherwise.
      */
    public boolean hasIdentifier() {
      return fieldSetFlags()[0];
    }


    /**
      * Clears the value of the 'identifier' field.
      * @return This builder.
      */
    public titan.ccp.model.records.WindowedActivePowerRecord.Builder clearIdentifier() {
      identifier = null;
      fieldSetFlags()[0] = false;
      return this;
    }

    /**
      * Gets the value of the 'startTimestamp' field.
      * @return The value.
      */
    public long getStartTimestamp() {
      return startTimestamp;
    }


    /**
      * Sets the value of the 'startTimestamp' field.
      * @param value The value of 'startTimestamp'.
      * @return This builder.
      */
    public titan.ccp.model.records.WindowedActivePowerRecord.Builder setStartTimestamp(long value) {
      validate(fields()[1], value);
      this.startTimestamp = value;
      fieldSetFlags()[1] = true;
      return this;
    }

    /**
      * Checks whether the 'startTimestamp' field has been set.
      * @return True if the 'startTimestamp' field has been set, false otherwise.
      */
    public boolean hasStartTimestamp() {
      return fieldSetFlags()[1];
    }


    /**
      * Clears the value of the 'startTimestamp' field.
      * @return This builder.
      */
    public titan.ccp.model.records.WindowedActivePowerRecord.Builder clearStartTimestamp() {
      fieldSetFlags()[1] = false;
      return this;
    }

    /**
      * Gets the value of the 'endTimestamp' field.
      * @return The value.
      */
    public long getEndTimestamp() {
      return endTimestamp;
    }


    /**
      * Sets the value of the 'endTimestamp' field.
      * @param value The value of 'endTimestamp'.
      * @return This builder.
      */
    public titan.ccp.model.records.WindowedActivePowerRecord.Builder setEndTimestamp(long value) {
      validate(fields()[2], value);
      this.endTimestamp = value;
      fieldSetFlags()[2] = true;
      return this;
    }

    /**
      * Checks whether the 'endTimestamp' field has been set.
      * @return True if the 'endTimestamp' field has been set, false otherwise.
      */
    public boolean hasEndTimestamp() {
      return fieldSetFlags()[2];
    }


    /**
      * Clears the value of the 'endTimestamp' field.
      * @return This builder.
      */
    public titan.ccp.model.records.WindowedActivePowerRecord.Builder clearEndTimestamp() {
      fieldSetFlags()[2] = false;
      return this;
    }

    /**
      * Gets the value of the 'count' field.
      * @return The value.
      */
    public long getCount() {
      return count;
    }


    /**
      * Sets the value of the 'count' field.
      * @param value The value of 'count'.
      * @return This builder.
      */
    public titan.ccp.model.records.WindowedActivePowerRecord.Builder setCount(long value) {
      validate(fields()[3], value);
      this.count = value;
      fieldSetFlags()[3] = true;
      return this;
    }

    /**
      * Checks whether the 'count' field has been set.
      * @return True if the 'count' field has been set, false otherwise.
      */
    public boolean hasCount() {
      return fieldSetFlags()[3];
    }


    /**
      * Clears the value of the 'count' field.
      * @return This builder.
      */
    public titan.ccp.model.records.WindowedActivePowerRecord.Builder clearCount() {
      fieldSetFlags()[3] = false;
      return this;
    }

    /**
      * Gets the value of the 'mean' field.
      * @return The value.
      */
    public double getMean() {
      return mean;
    }


    /**
      * Sets the value of the 'mean' field.
      * @param value The value of 'mean'.
      * @return This builder.
      */
    public titan.ccp.model.records.WindowedActivePowerRecord.Builder setMean(double value) {
      validate(fields()[4], value);
      this.mean = value;
      fieldSetFlags()[4] = true;
      return this;
    }

    /**
      * Checks whether the 'mean' field has been set.
      * @return True if the 'mean' field has been set, false otherwise.
      */
    public boolean hasMean() {
      return fieldSetFlags()[4];
    }


    /**
      * Clears the value of the 'mean' field.
      * @return This builder.
      */
    public titan.ccp.model.records.WindowedActivePowerRecord.Builder clearMean() {
      fieldSetFlags()[4] = false;
      return this;
    }

    /**
      * Gets the value of the 'populationVariance' field.
      * @return The value.
      */
    public double getPopulationVariance() {
      return populationVariance;
    }


    /**
      * Sets the value of the 'populationVariance' field.
      * @param value The value of 'populationVariance'.
      * @return This builder.
      */
    public titan.ccp.model.records.WindowedActivePowerRecord.Builder setPopulationVariance(double value) {
      validate(fields()[5], value);
      this.populationVariance = value;
      fieldSetFlags()[5] = true;
      return this;
    }

    /**
      * Checks whether the 'populationVariance' field has been set.
      * @return True if the 'populationVariance' field has been set, false otherwise.
      */
    public boolean hasPopulationVariance() {
      return fieldSetFlags()[5];
    }


    /**
      * Clears the value of the 'populationVariance' field.
      * @return This builder.
      */
    public titan.ccp.model.records.WindowedActivePowerRecord.Builder clearPopulationVariance() {
      fieldSetFlags()[5] = false;
      return this;
    }

    /**
      * Gets the value of the 'min' field.
      * @return The value.
      */
    public double getMin() {
      return min;
    }


    /**
      * Sets the value of the 'min' field.
      * @param value The value of 'min'.
      * @return This builder.
      */
    public titan.ccp.model.records.WindowedActivePowerRecord.Builder setMin(double value) {
      validate(fields()[6], value);
      this.min = value;
      fieldSetFlags()[6] = true;
      return this;
    }

    /**
      * Checks whether the 'min' field has been set.
      * @return True if the 'min' field has been set, false otherwise.
      */
    public boolean hasMin() {
      return fieldSetFlags()[6];
    }


    /**
      * Clears the value of the 'min' field.
      * @return This builder.
      */
    public titan.ccp.model.records.WindowedActivePowerRecord.Builder clearMin() {
      fieldSetFlags()[6] = false;
      return this;
    }

    /**
      * Gets the value of the 'max' field.
      * @return The value.
      */
    public double getMax() {
      return max;
    }


    /**
      * Sets the value of the 'max' field.
      * @param value The value of 'max'.
      * @return This builder.
      */
    public titan.ccp.model.records.WindowedActivePowerRecord.Builder setMax(double value) {
      validate(fields()[7], value);
      this.max = value;
      fieldSetFlags()[7] = true;
      return this;
    }

    /**
      * Checks whether the 'max' field has been set.
      * @return True if the 'max' field has been set, false otherwise.
      */
    public boolean hasMax() {
      return fieldSetFlags()[7];
    }


    /**
      * Clears the value of the 'max' field.
      * @return This builder.
      */
    public titan.ccp.model.records.WindowedActivePowerRecord.Builder clearMax() {
      fieldSetFlags()[7] = false;
      return this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public WindowedActivePowerRecord build() {
      try {
        WindowedActivePowerRecord record = new WindowedActivePowerRecord();
        record.identifier = fieldSetFlags()[0] ? this.identifier : (java.lang.String) defaultValue(fields()[0]);
        record.startTimestamp = fieldSetFlags()[1] ? this.startTimestamp : (java.lang.Long) defaultValue(fields()[1]);
        record.endTimestamp = fieldSetFlags()[2] ? this.endTimestamp : (java.lang.Long) defaultValue(fields()[2]);
        record.count = fieldSetFlags()[3] ? this.count : (java.lang.Long) defaultValue(fields()[3]);
        record.mean = fieldSetFlags()[4] ? this.mean : (java.lang.Double) defaultValue(fields()[4]);
        record.populationVariance = fieldSetFlags()[5] ? this.populationVariance : (java.lang.Double) defaultValue(fields()[5]);
        record.min = fieldSetFlags()[6] ? this.min : (java.lang.Double) defaultValue(fields()[6]);
        record.max = fieldSetFlags()[7] ? this.max : (java.lang.Double) defaultValue(fields()[7]);
        return record;
      } catch (org.apache.avro.AvroMissingFieldException e) {
        throw e;
      } catch (java.lang.Exception e) {
        throw new org.apache.avro.AvroRuntimeException(e);
      }
    }
  }

  @SuppressWarnings("unchecked")
  private static final org.apache.avro.io.DatumWriter<WindowedActivePowerRecord>
    WRITER$ = (org.apache.avro.io.DatumWriter<WindowedActivePowerRecord>)MODEL$.createDatumWriter(SCHEMA$);

  @Override public void writeExternal(java.io.ObjectOutput out)
    throws java.io.IOException {
    WRITER$.write(this, SpecificData.getEncoder(out));
  }

  @SuppressWarnings("unchecked")
  private static final org.apache.avro.io.DatumReader<WindowedActivePowerRecord>
    READER$ = (org.apache.avro.io.DatumReader<WindowedActivePowerRecord>)MODEL$.createDatumReader(SCHEMA$);

  @Override public void readExternal(java.io.ObjectInput in)
    throws java.io.IOException {
    READER$.read(this, SpecificData.getDecoder(in));
  }

  @Override protected boolean hasCustomCoders() { return true; }

  @Override public void customEncode(org.apache.avro.io.Encoder out)
    throws java.io.IOException
  {
    out.writeString(this.identifier);

    out.writeLong(this.startTimestamp);

    out.writeLong(this.endTimestamp);

    out.writeLong(this.count);

    out.writeDouble(this.mean);

    out.writeDouble(this.populationVariance);

    out.writeDouble(this.min);

    out.writeDouble(this.max);

  }

  @Override public void customDecode(org.apache.avro.io.ResolvingDecoder in)
    throws java.io.IOException
  {
    org.apache.avro.Schema.Field[] fieldOrder = in.readFieldOrderIfDiff();
    if (fieldOrder == null) {
      this.identifier = in.readString();

      this.startTimestamp = in.readLong();

      this.endTimestamp = in.readLong();

      this.count = in.readLong();

      this.mean = in.readDouble();

      this.populationVariance = in.readDouble();

      this.min = in.readDouble();

      this.max = in.readDouble();

    } else {
      for (int i = 0; i < 8; i++) {
        switch (fieldOrder[i].pos()) {
        case 0:
          this.identifier = in.readString();
          break;

        case 1:
          this.startTimestamp = in.readLong();
          break;

        case 2:
          this.endTimestamp = in.readLong();
          break;

        case 3:
          this.count = in.readLong();
          break;

        case 4:
          this.mean = in.readDouble();
          break;

        case 5:
          this.populationVariance = in.readDouble();
          break;

        case 6:
          this.min = in.readDouble();
          break;

        case 7:
          this.max = in.readDouble();
          break;

        default:
          throw new java.io.IOException("Corrupt ResolvingDecoder.");
        }
      }
    }
  }
}










