package titan.ccp.models.records;

import java.nio.BufferOverflowException;
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;

import kieker.common.record.AbstractMonitoringRecord;
import kieker.common.record.IMonitoringRecord;
import kieker.common.util.registry.IRegistry;


/**
 * @author Sören Henning
 * 
 * @since 
 */
public class PowerConsumptionRecord extends AbstractMonitoringRecord implements IMonitoringRecord.Factory, IMonitoringRecord.BinaryFactory {
	private static final long serialVersionUID = -2123141684241450260L;

		/** Descriptive definition of the serialization size of the record. */
		public static final int SIZE = TYPE_SIZE_BYTE // PowerConsumptionRecord.identifier
				 + TYPE_SIZE_LONG // PowerConsumptionRecord.timestamp
				 + TYPE_SIZE_INT // PowerConsumptionRecord.powerConsumptionInWh
		;
	
		public static final Class<?>[] TYPES = {
			byte[].class, // PowerConsumptionRecord.identifier
			long.class, // PowerConsumptionRecord.timestamp
			int.class, // PowerConsumptionRecord.powerConsumptionInWh
		};
	
	/** user-defined constants */

	/** default constants */

	/** property declarations */
	private final byte[] identifier;
	private final long timestamp;
	private final int powerConsumptionInWh;

	/**
	 * Creates a new instance of this class using the given parameters.
	 * 
	 * @param identifier
	 *            identifier
	 * @param timestamp
	 *            timestamp
	 * @param powerConsumptionInWh
	 *            powerConsumptionInWh
	 */
	public PowerConsumptionRecord(final byte[] identifier, final long timestamp, final int powerConsumptionInWh) {
		this.identifier = identifier;
		this.timestamp = timestamp;
		this.powerConsumptionInWh = powerConsumptionInWh;
	}

	/**
	 * This constructor converts the given array into a record.
	 * It is recommended to use the array which is the result of a call to {@link #toArray()}.
	 * 
	 * @param values
	 *            The values for the record.
	 */
	public PowerConsumptionRecord(final Object[] values) { // NOPMD (direct store of values)
		AbstractMonitoringRecord.checkArray(values, TYPES);
		this.identifier = (byte[]) values[0];
		this.timestamp = (Long) values[1];
		this.powerConsumptionInWh = (Integer) values[2];
	}

	/**
	 * This constructor uses the given array to initialize the fields of this record.
	 * 
	 * @param values
	 *            The values for the record.
	 * @param valueTypes
	 *            The types of the elements in the first array.
	 */
	protected PowerConsumptionRecord(final Object[] values, final Class<?>[] valueTypes) { // NOPMD (values stored directly)
		AbstractMonitoringRecord.checkArray(values, valueTypes);
		this.identifier = (byte[]) values[0];
		this.timestamp = (Long) values[1];
		this.powerConsumptionInWh = (Integer) values[2];
	}

	/**
	 * This constructor converts the given array into a record.
	 * 
	 * @param buffer
	 *            The bytes for the record.
	 * 
	 * @throws BufferUnderflowException
	 *             if buffer not sufficient
	 */
	public PowerConsumptionRecord(final ByteBuffer buffer, final IRegistry<String> stringRegistry) throws BufferUnderflowException {
		// load array sizes
		this.identifier = new byte[8];
		for (int i0=0;i0<8;i0++)
			this.identifier[i0] = buffer.get();
		
		this.timestamp = buffer.getLong();
		this.powerConsumptionInWh = buffer.getInt();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object[] toArray() {
		return new Object[] {
			this.getIdentifier(),
			this.getTimestamp(),
			this.getPowerConsumptionInWh()
		};
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void registerStrings(final IRegistry<String> stringRegistry) {	// NOPMD (generated code)
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void writeBytes(final ByteBuffer buffer, final IRegistry<String> stringRegistry) throws BufferOverflowException {
		// store array sizes
		for (int i0=0;i0<8;i0++)
			buffer.put((byte)this.getIdentifier()[i0]);
		
		buffer.putLong(this.getTimestamp());
		buffer.putInt(this.getPowerConsumptionInWh());
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
	 * 
	 * @deprecated This record uses the {@link kieker.common.record.IMonitoringRecord.BinaryFactory} mechanism. Hence, this method is not implemented.
	 */
	@Override
	@Deprecated
	public void initFromBytes(final ByteBuffer buffer, final IRegistry<String> stringRegistry) throws BufferUnderflowException {
		throw new UnsupportedOperationException();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean equals(final Object obj) {
		if (obj == null) return false;
		if (obj == this) return true;
		if (obj.getClass() != this.getClass()) return false;
		
		final PowerConsumptionRecord castedRecord = (PowerConsumptionRecord) obj;
		if (this.getLoggingTimestamp() != castedRecord.getLoggingTimestamp()) return false;
		// get array length
		for (int i0=0;i0<8;i0++)
			if (this.getIdentifier()[i0] != castedRecord.getIdentifier()[i0]) return false;
		
		if (this.getTimestamp() != castedRecord.getTimestamp()) return false;
		if (this.getPowerConsumptionInWh() != castedRecord.getPowerConsumptionInWh()) return false;
		return true;
	}
	
	public final byte[] getIdentifier() {
		return this.identifier;
	}	
	
	public final long getTimestamp() {
		return this.timestamp;
	}	
	
	public final int getPowerConsumptionInWh() {
		return this.powerConsumptionInWh;
	}	
}
