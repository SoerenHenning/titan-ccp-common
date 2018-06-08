package titan.ccp.models.records;


import kieker.common.exception.RecordInstantiationException;
import kieker.common.record.factory.IRecordFactory;
import kieker.common.record.io.IValueDeserializer;

/**
 * @author Generic Kieker
 * 
 * @since 1.14
 */
public final class AggregatedActivePowerFactory implements IRecordFactory<AggregatedActivePower> {
	
	
	@Override
	public AggregatedActivePower create(final IValueDeserializer deserializer) throws RecordInstantiationException {
		return new AggregatedActivePower(deserializer);
	}
	
	@Override
	@Deprecated
	public AggregatedActivePower create(final Object[] values) {
		return new AggregatedActivePower(values);
	}
	
	public int getRecordSizeInBytes() {
		return AggregatedActivePower.SIZE;
	}
}
