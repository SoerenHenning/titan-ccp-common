package titan.ccp.models.records.serialization.kafka;

import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;

import titan.ccp.models.records.AggregatedPowerConsumptionRecord;
import titan.ccp.models.records.PowerConsumptionRecord;

public final class RecordSerdes {

	public static final Serde<PowerConsumptionRecord> forPowerConsumptionRecord() {
		return Serdes.serdeFrom(new PowerConsumptionRecordSerializer(), new PowerConsumptionRecordDeserializer());
	}
	
	public static final Serde<AggregatedPowerConsumptionRecord> forAggregatedPowerConsumptionRecord() {
		return Serdes.serdeFrom(new AggregatedPowerConsumptionRecordSerializer(), new AggregatedPowerConsumptionRecordDeserializer());
	}
	
}
