package titan.ccp.models.records.serialization.kafka;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.Map;

import org.apache.kafka.common.serialization.ByteBufferDeserializer;
import org.apache.kafka.common.serialization.Deserializer;

import titan.ccp.models.records.PowerConsumptionRecord;

public class PowerConsumptionRecordDeserializer implements Deserializer<PowerConsumptionRecord> {

	private final ByteBufferDeserializer byteBufferDeserializer = new ByteBufferDeserializer();
	private static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");

	@Override
	public void configure(final Map<String, ?> configs, final boolean isKey) {
		this.byteBufferDeserializer.configure(configs, isKey);
	}

	@Override
	public PowerConsumptionRecord deserialize(final String topic, final byte[] data) {
		final ByteBuffer buffer = this.byteBufferDeserializer.deserialize(topic, data);

		final int stringLength = buffer.getInt();
		final byte[] stringBytes = new byte[stringLength];
		buffer.get(stringBytes);
		final String identifier = new String(stringBytes, DEFAULT_CHARSET);
		final long timestamp = buffer.getLong();
		final int powerConsumption = buffer.getInt();

		return new PowerConsumptionRecord(identifier, timestamp, powerConsumption);
	}

	@Override
	public void close() {
		this.byteBufferDeserializer.close();
	}

}
