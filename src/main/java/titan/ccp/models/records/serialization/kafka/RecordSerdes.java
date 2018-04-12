package titan.ccp.models.records.serialization.kafka;

import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;

import titan.ccp.models.records.PowerConsumptionRecord;

public final class RecordSerdes {

	public static final Serde<PowerConsumptionRecord> forPowerConsumptionRecord() {
		return Serdes.serdeFrom(new PowerConsumptionRecordSerializer(), new PowerConsumptionRecordDeserializer());
	}
	
}
