package titan.ccp.models.records;

import java.nio.BufferOverflowException;

import kieker.common.exception.RecordInstantiationException;
import kieker.common.record.AbstractMonitoringRecord;
import kieker.common.record.IMonitoringRecord;
import kieker.common.record.io.IValueDeserializer;
import kieker.common.record.io.IValueSerializer;


/**
 * @author Generic Kieker
 * API compatibility: Kieker 1.14.0
 * 
 * @since 1.14
 */
public class AggregatedPowerConsumptionRecord extends AbstractMonitoringRecord implements IMonitoringRecord.Factory, IMonitoringRecord.BinaryFactory {			
	/** Descriptive definition of the serialization size of the record. */
	public static final int SIZE = TYPE_SIZE_STRING // AggregatedPowerConsumptionRecord.identifier
			 + TYPE_SIZE_LONG // AggregatedPowerConsumptionRecord.timestamp
			 + TYPE_SIZE_INT // AggregatedPowerConsumptionRecord.min
			 + TYPE_SIZE_INT // AggregatedPowerConsumptionRecord.max
			 + TYPE_SIZE_LONG // AggregatedPowerConsumptionRecord.count
			 + TYPE_SIZE_LONG // AggregatedPowerConsumptionRecord.sum
			 + TYPE_SIZE_DOUBLE; // AggregatedPowerConsumptionRecord.average
	
	public static final Class<?>[] TYPES = {
		String.class, // AggregatedPowerConsumptionRecord.identifier
		long.class, // AggregatedPowerConsumptionRecord.timestamp
		int.class, // AggregatedPowerConsumptionRecord.min
		int.class, // AggregatedPowerConsumptionRecord.max
		long.class, // AggregatedPowerConsumptionRecord.count
		long.class, // AggregatedPowerConsumptionRecord.sum
		double.class, // AggregatedPowerConsumptionRecord.average
	};
	
	/** default constants. */
	public static final String IDENTIFIER = "";
	private static final long serialVersionUID = -8735442092439463989L;
	
	/** property name array. */
	private static final String[] PROPERTY_NAMES = {
		"identifier",
		"timestamp",
		"min",
		"max",
		"count",
		"sum",
		"average",
	};
	
	/** property declarations. */
	private final String identifier;
	private final long timestamp;
	private final int min;
	private final int max;
	private final long count;
	private final long sum;
	private final double average;
	
	/**
	 * Creates a new instance of this class using the given parameters.
	 * 
	 * @param identifier
	 *            identifier
	 * @param timestamp
	 *            timestamp
	 * @param min
	 *            min
	 * @param max
	 *            max
	 * @param count
	 *            count
	 * @param sum
	 *            sum
	 * @param average
	 *            average
	 */
	public AggregatedPowerConsumptionRecord(final String identifier, final long timestamp, final int min, final int max, final long count, final long sum, final double average) {
		this.identifier = identifier == null?"":identifier;
		this.timestamp = timestamp;
		this.min = min;
		this.max = max;
		this.count = count;
		this.sum = sum;
		this.average = average;
	}

	/**
	 * This constructor converts the given array into a record.
	 * It is recommended to use the array which is the result of a call to {@link #toArray()}.
	 * 
	 * @param values
	 *            The values for the record.
	 *
	 * @deprecated since 1.13. Use {@link #AggregatedPowerConsumptionRecord(IValueDeserializer)} instead.
	 */
	@Deprecated
	public AggregatedPowerConsumptionRecord(final Object[] values) { // NOPMD (direct store of values)
		AbstractMonitoringRecord.checkArray(values, TYPES);
		this.identifier = (String) values[0];
		this.timestamp = (Long) values[1];
		this.min = (Integer) values[2];
		this.max = (Integer) values[3];
		this.count = (Long) values[4];
		this.sum = (Long) values[5];
		this.average = (Double) values[6];
	}

	/**
	 * This constructor uses the given array to initialize the fields of this record.
	 * 
	 * @param values
	 *            The values for the record.
	 * @param valueTypes
	 *            The types of the elements in the first array.
	 *
	 * @deprecated since 1.13. Use {@link #AggregatedPowerConsumptionRecord(IValueDeserializer)} instead.
	 */
	@Deprecated
	protected AggregatedPowerConsumptionRecord(final Object[] values, final Class<?>[] valueTypes) { // NOPMD (values stored directly)
		AbstractMonitoringRecord.checkArray(values, valueTypes);
		this.identifier = (String) values[0];
		this.timestamp = (Long) values[1];
		this.min = (Integer) values[2];
		this.max = (Integer) values[3];
		this.count = (Long) values[4];
		this.sum = (Long) values[5];
		this.average = (Double) values[6];
	}

	
	/**
	 * @param deserializer
	 *            The deserializer to use
	 * @throws RecordInstantiationException 
	 *            when the record could not be deserialized
	 */
	public AggregatedPowerConsumptionRecord(final IValueDeserializer deserializer) throws RecordInstantiationException {
		this.identifier = deserializer.getString();
		this.timestamp = deserializer.getLong();
		this.min = deserializer.getInt();
		this.max = deserializer.getInt();
		this.count = deserializer.getLong();
		this.sum = deserializer.getLong();
		this.average = deserializer.getDouble();
	}
	
	/**
	 * {@inheritDoc}
	 *
	 * @deprecated since 1.13. Use {@link #serialize(IValueSerializer)} with an array serializer instead.
	 */
	@Override
	@Deprecated
	public Object[] toArray() {
		return new Object[] {
			this.getIdentifier(),
			this.getTimestamp(),
			this.getMin(),
			this.getMax(),
			this.getCount(),
			this.getSum(),
			this.getAverage(),
		};
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void serialize(final IValueSerializer serializer) throws BufferOverflowException {
		//super.serialize(serializer);
		serializer.putString(this.getIdentifier());
		serializer.putLong(this.getTimestamp());
		serializer.putInt(this.getMin());
		serializer.putInt(this.getMax());
		serializer.putLong(this.getCount());
		serializer.putLong(this.getSum());
		serializer.putDouble(this.getAverage());
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
		return PROPERTY_NAMES; // NOPMD
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
	 * 
	 * @deprecated This record uses the {@link kieker.common.record.IMonitoringRecord.Factory} mechanism. Hence, this method is not implemented.
	 */
	@Override
	@Deprecated
	public void initFromArray(final Object[] values) {
		throw new UnsupportedOperationException();
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
		
		final AggregatedPowerConsumptionRecord castedRecord = (AggregatedPowerConsumptionRecord) obj;
		if (this.getLoggingTimestamp() != castedRecord.getLoggingTimestamp()) {
			return false;
		}
		if (!this.getIdentifier().equals(castedRecord.getIdentifier())) {
			return false;
		}
		if (this.getTimestamp() != castedRecord.getTimestamp()) {
			return false;
		}
		if (this.getMin() != castedRecord.getMin()) {
			return false;
		}
		if (this.getMax() != castedRecord.getMax()) {
			return false;
		}
		if (this.getCount() != castedRecord.getCount()) {
			return false;
		}
		if (this.getSum() != castedRecord.getSum()) {
			return false;
		}
		if (isNotEqual(this.getAverage(), castedRecord.getAverage())) {
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
	
	
	public final int getMin() {
		return this.min;
	}
	
	
	public final int getMax() {
		return this.max;
	}
	
	
	public final long getCount() {
		return this.count;
	}
	
	
	public final long getSum() {
		return this.sum;
	}
	
	
	public final double getAverage() {
		return this.average;
	}
	
}
