package titan.ccp.models.records;


import kieker.common.exception.RecordInstantiationException;
import kieker.common.record.factory.IRecordFactory;
import kieker.common.record.io.IValueDeserializer;

/**
 * @author Generic Kieker
 * 
 * @since 1.14
 */
public final class AAEntryCallFactory implements IRecordFactory<AAEntryCall> {
	
	
	@Override
	public AAEntryCall create(final IValueDeserializer deserializer) throws RecordInstantiationException {
		return new AAEntryCall(deserializer);
	}
	
	@Override
	@Deprecated
	public AAEntryCall create(final Object[] values) {
		return new AAEntryCall(values);
	}
	
	public int getRecordSizeInBytes() {
		return AAEntryCall.SIZE;
	}
}
