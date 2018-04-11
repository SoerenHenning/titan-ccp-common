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
public class SecondEntryCall extends AbstractMonitoringRecord implements IMonitoringRecord.Factory, IMonitoringRecord.BinaryFactory {			
	/** Descriptive definition of the serialization size of the record. */
	public static final int SIZE = TYPE_SIZE_STRING // SecondEntryCall.session
			 + TYPE_SIZE_LONG // SecondEntryCall.entryTime
			 + TYPE_SIZE_LONG // SecondEntryCall.exitTime
			 + TYPE_SIZE_STRING // SecondEntryCall.operationSignature
			 + TYPE_SIZE_STRING; // SecondEntryCall.componentSignature
	
	public static final Class<?>[] TYPES = {
		String.class, // SecondEntryCall.session
		long.class, // SecondEntryCall.entryTime
		long.class, // SecondEntryCall.exitTime
		String.class, // SecondEntryCall.operationSignature
		String.class, // SecondEntryCall.componentSignature
	};
	
	/** default constants. */
	public static final String SESSION = "<no session>";
	public static final String OPERATION_SIGNATURE = "<no operation>";
	public static final String COMPONENT_SIGNATURE = "<no component>";
	private static final long serialVersionUID = -1967287647446243422L;
	
	/** property name array. */
	private static final String[] PROPERTY_NAMES = {
		"session",
		"entryTime",
		"exitTime",
		"operationSignature",
		"componentSignature",
	};
	
	/** property declarations. */
	private final String session;
	private final long entryTime;
	private final long exitTime;
	private final String operationSignature;
	private final String componentSignature;
	
	/**
	 * Creates a new instance of this class using the given parameters.
	 * 
	 * @param session
	 *            session
	 * @param entryTime
	 *            entryTime
	 * @param exitTime
	 *            exitTime
	 * @param operationSignature
	 *            operationSignature
	 * @param componentSignature
	 *            componentSignature
	 */
	public SecondEntryCall(final String session, final long entryTime, final long exitTime, final String operationSignature, final String componentSignature) {
		this.session = session == null?SESSION:session;
		this.entryTime = entryTime;
		this.exitTime = exitTime;
		this.operationSignature = operationSignature == null?OPERATION_SIGNATURE:operationSignature;
		this.componentSignature = componentSignature == null?COMPONENT_SIGNATURE:componentSignature;
	}

	/**
	 * This constructor converts the given array into a record.
	 * It is recommended to use the array which is the result of a call to {@link #toArray()}.
	 * 
	 * @param values
	 *            The values for the record.
	 *
	 * @deprecated since 1.13. Use {@link #SecondEntryCall(IValueDeserializer)} instead.
	 */
	@Deprecated
	public SecondEntryCall(final Object[] values) { // NOPMD (direct store of values)
		AbstractMonitoringRecord.checkArray(values, TYPES);
		this.session = (String) values[0];
		this.entryTime = (Long) values[1];
		this.exitTime = (Long) values[2];
		this.operationSignature = (String) values[3];
		this.componentSignature = (String) values[4];
	}

	/**
	 * This constructor uses the given array to initialize the fields of this record.
	 * 
	 * @param values
	 *            The values for the record.
	 * @param valueTypes
	 *            The types of the elements in the first array.
	 *
	 * @deprecated since 1.13. Use {@link #SecondEntryCall(IValueDeserializer)} instead.
	 */
	@Deprecated
	protected SecondEntryCall(final Object[] values, final Class<?>[] valueTypes) { // NOPMD (values stored directly)
		AbstractMonitoringRecord.checkArray(values, valueTypes);
		this.session = (String) values[0];
		this.entryTime = (Long) values[1];
		this.exitTime = (Long) values[2];
		this.operationSignature = (String) values[3];
		this.componentSignature = (String) values[4];
	}

	
	/**
	 * @param deserializer
	 *            The deserializer to use
	 * @throws RecordInstantiationException 
	 *            when the record could not be deserialized
	 */
	public SecondEntryCall(final IValueDeserializer deserializer) throws RecordInstantiationException {
		this.session = deserializer.getString();
		this.entryTime = deserializer.getLong();
		this.exitTime = deserializer.getLong();
		this.operationSignature = deserializer.getString();
		this.componentSignature = deserializer.getString();
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
			this.getSession(),
			this.getEntryTime(),
			this.getExitTime(),
			this.getOperationSignature(),
			this.getComponentSignature(),
		};
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void serialize(final IValueSerializer serializer) throws BufferOverflowException {
		//super.serialize(serializer);
		serializer.putString(this.getSession());
		serializer.putLong(this.getEntryTime());
		serializer.putLong(this.getExitTime());
		serializer.putString(this.getOperationSignature());
		serializer.putString(this.getComponentSignature());
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
		
		final SecondEntryCall castedRecord = (SecondEntryCall) obj;
		if (this.getLoggingTimestamp() != castedRecord.getLoggingTimestamp()) {
			return false;
		}
		if (!this.getSession().equals(castedRecord.getSession())) {
			return false;
		}
		if (this.getEntryTime() != castedRecord.getEntryTime()) {
			return false;
		}
		if (this.getExitTime() != castedRecord.getExitTime()) {
			return false;
		}
		if (!this.getOperationSignature().equals(castedRecord.getOperationSignature())) {
			return false;
		}
		if (!this.getComponentSignature().equals(castedRecord.getComponentSignature())) {
			return false;
		}
		
		return true;
	}
	
	public final String getSession() {
		return this.session;
	}
	
	
	public final long getEntryTime() {
		return this.entryTime;
	}
	
	
	public final long getExitTime() {
		return this.exitTime;
	}
	
	
	public final String getOperationSignature() {
		return this.operationSignature;
	}
	
	
	public final String getComponentSignature() {
		return this.componentSignature;
	}
	
}
