package titan.ccp.models.records;


import kieker.common.exception.RecordInstantiationException;
import kieker.common.record.factory.IRecordFactory;
import kieker.common.record.io.IValueDeserializer;

/**
 * @author Generic Kieker
 * 
 * @since 1.14
 */
public final class AggregatedPowerConsumptionRecordFactory implements IRecordFactory<AggregatedPowerConsumptionRecord> {
	
	
	@Override
	public AggregatedPowerConsumptionRecord create(final IValueDeserializer deserializer) throws RecordInstantiationException {
		return new AggregatedPowerConsumptionRecord(deserializer);
	}
	
	@Override
	@Deprecated
	public AggregatedPowerConsumptionRecord create(final Object[] values) {
		return new AggregatedPowerConsumptionRecord(values);
	}
	
	public int getRecordSizeInBytes() {
		return AggregatedPowerConsumptionRecord.SIZE;
	}
}
