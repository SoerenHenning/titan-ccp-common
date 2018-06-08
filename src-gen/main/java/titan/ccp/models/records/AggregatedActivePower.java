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
public class AggregatedActivePower extends AbstractMonitoringRecord implements IMonitoringRecord.Factory, IMonitoringRecord.BinaryFactory {			
	/** Descriptive definition of the serialization size of the record. */
	public static final int SIZE = TYPE_SIZE_STRING // AggregatedActivePower.identifier
			 + TYPE_SIZE_LONG // AggregatedActivePower.timestamp
			 + TYPE_SIZE_DOUBLE // AggregatedActivePower.minInWh
			 + TYPE_SIZE_DOUBLE // AggregatedActivePower.maxInWh
			 + TYPE_SIZE_LONG // AggregatedActivePower.count
			 + TYPE_SIZE_DOUBLE // AggregatedActivePower.sumInWh
			 + TYPE_SIZE_DOUBLE; // AggregatedActivePower.averageInWh
	
	public static final Class<?>[] TYPES = {
		String.class, // AggregatedActivePower.identifier
		long.class, // AggregatedActivePower.timestamp
		double.class, // AggregatedActivePower.minInWh
		double.class, // AggregatedActivePower.maxInWh
		long.class, // AggregatedActivePower.count
		double.class, // AggregatedActivePower.sumInWh
		double.class, // AggregatedActivePower.averageInWh
	};
	
	/** default constants. */
	public static final String IDENTIFIER = "";
	private static final long serialVersionUID = 110565773346581561L;
	
	/** property name array. */
	private static final String[] PROPERTY_NAMES = {
		"identifier",
		"timestamp",
		"minInWh",
		"maxInWh",
		"count",
		"sumInWh",
		"averageInWh",
	};
	
	/** property declarations. */
	private final String identifier;
	private final long timestamp;
	private final double minInWh;
	private final double maxInWh;
	private final long count;
	private final double sumInWh;
	private final double averageInWh;
	
	/**
	 * Creates a new instance of this class using the given parameters.
	 * 
	 * @param identifier
	 *            identifier
	 * @param timestamp
	 *            timestamp
	 * @param minInWh
	 *            minInWh
	 * @param maxInWh
	 *            maxInWh
	 * @param count
	 *            count
	 * @param sumInWh
	 *            sumInWh
	 * @param averageInWh
	 *            averageInWh
	 */
	public AggregatedActivePower(final String identifier, final long timestamp, final double minInWh, final double maxInWh, final long count, final double sumInWh, final double averageInWh) {
		this.identifier = identifier == null?"":identifier;
		this.timestamp = timestamp;
		this.minInWh = minInWh;
		this.maxInWh = maxInWh;
		this.count = count;
		this.sumInWh = sumInWh;
		this.averageInWh = averageInWh;
	}

	/**
	 * This constructor converts the given array into a record.
	 * It is recommended to use the array which is the result of a call to {@link #toArray()}.
	 * 
	 * @param values
	 *            The values for the record.
	 *
	 * @deprecated since 1.13. Use {@link #AggregatedActivePower(IValueDeserializer)} instead.
	 */
	@Deprecated
	public AggregatedActivePower(final Object[] values) { // NOPMD (direct store of values)
		AbstractMonitoringRecord.checkArray(values, TYPES);
		this.identifier = (String) values[0];
		this.timestamp = (Long) values[1];
		this.minInWh = (Double) values[2];
		this.maxInWh = (Double) values[3];
		this.count = (Long) values[4];
		this.sumInWh = (Double) values[5];
		this.averageInWh = (Double) values[6];
	}

	/**
	 * This constructor uses the given array to initialize the fields of this record.
	 * 
	 * @param values
	 *            The values for the record.
	 * @param valueTypes
	 *            The types of the elements in the first array.
	 *
	 * @deprecated since 1.13. Use {@link #AggregatedActivePower(IValueDeserializer)} instead.
	 */
	@Deprecated
	protected AggregatedActivePower(final Object[] values, final Class<?>[] valueTypes) { // NOPMD (values stored directly)
		AbstractMonitoringRecord.checkArray(values, valueTypes);
		this.identifier = (String) values[0];
		this.timestamp = (Long) values[1];
		this.minInWh = (Double) values[2];
		this.maxInWh = (Double) values[3];
		this.count = (Long) values[4];
		this.sumInWh = (Double) values[5];
		this.averageInWh = (Double) values[6];
	}

	
	/**
	 * @param deserializer
	 *            The deserializer to use
	 * @throws RecordInstantiationException 
	 *            when the record could not be deserialized
	 */
	public AggregatedActivePower(final IValueDeserializer deserializer) throws RecordInstantiationException {
		this.identifier = deserializer.getString();
		this.timestamp = deserializer.getLong();
		this.minInWh = deserializer.getDouble();
		this.maxInWh = deserializer.getDouble();
		this.count = deserializer.getLong();
		this.sumInWh = deserializer.getDouble();
		this.averageInWh = deserializer.getDouble();
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
			this.getMinInWh(),
			this.getMaxInWh(),
			this.getCount(),
			this.getSumInWh(),
			this.getAverageInWh(),
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
		serializer.putDouble(this.getMinInWh());
		serializer.putDouble(this.getMaxInWh());
		serializer.putLong(this.getCount());
		serializer.putDouble(this.getSumInWh());
		serializer.putDouble(this.getAverageInWh());
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
		
		final AggregatedActivePower castedRecord = (AggregatedActivePower) obj;
		if (this.getLoggingTimestamp() != castedRecord.getLoggingTimestamp()) {
			return false;
		}
		if (!this.getIdentifier().equals(castedRecord.getIdentifier())) {
			return false;
		}
		if (this.getTimestamp() != castedRecord.getTimestamp()) {
			return false;
		}
		if (isNotEqual(this.getMinInWh(), castedRecord.getMinInWh())) {
			return false;
		}
		if (isNotEqual(this.getMaxInWh(), castedRecord.getMaxInWh())) {
			return false;
		}
		if (this.getCount() != castedRecord.getCount()) {
			return false;
		}
		if (isNotEqual(this.getSumInWh(), castedRecord.getSumInWh())) {
			return false;
		}
		if (isNotEqual(this.getAverageInWh(), castedRecord.getAverageInWh())) {
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
	
	
	public final double getMinInWh() {
		return this.minInWh;
	}
	
	
	public final double getMaxInWh() {
		return this.maxInWh;
	}
	
	
	public final long getCount() {
		return this.count;
	}
	
	
	public final double getSumInWh() {
		return this.sumInWh;
	}
	
	
	public final double getAverageInWh() {
		return this.averageInWh;
	}
	
}
