package titan.ccp.models.records;


import kieker.common.exception.RecordInstantiationException;
import kieker.common.record.factory.IRecordFactory;
import kieker.common.record.io.IValueDeserializer;

/**
 * @author Generic Kieker
 * 
 * @since 1.14
 */
public final class PowerConsumptionRecordFactory implements IRecordFactory<PowerConsumptionRecord> {
	
	
	@Override
	public PowerConsumptionRecord create(final IValueDeserializer deserializer) throws RecordInstantiationException {
		return new PowerConsumptionRecord(deserializer);
	}
	
	@Override
	@Deprecated
	public PowerConsumptionRecord create(final Object[] values) {
		return new PowerConsumptionRecord(values);
	}
	
	public int getRecordSizeInBytes() {
		return PowerConsumptionRecord.SIZE;
	}
}
