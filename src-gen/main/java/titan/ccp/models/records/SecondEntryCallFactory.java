package titan.ccp.models.records;


import kieker.common.exception.RecordInstantiationException;
import kieker.common.record.factory.IRecordFactory;
import kieker.common.record.io.IValueDeserializer;

/**
 * @author Generic Kieker
 * 
 * @since 1.14
 */
public final class SecondEntryCallFactory implements IRecordFactory<SecondEntryCall> {
	
	
	@Override
	public SecondEntryCall create(final IValueDeserializer deserializer) throws RecordInstantiationException {
		return new SecondEntryCall(deserializer);
	}
	
	@Override
	@Deprecated
	public SecondEntryCall create(final Object[] values) {
		return new SecondEntryCall(values);
	}
	
	public int getRecordSizeInBytes() {
		return SecondEntryCall.SIZE;
	}
}
