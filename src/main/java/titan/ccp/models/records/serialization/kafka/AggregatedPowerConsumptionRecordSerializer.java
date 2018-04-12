package titan.ccp.models.records.serialization.kafka;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.Map;

import org.apache.kafka.common.serialization.ByteBufferSerializer;
import org.apache.kafka.common.serialization.Serializer;

import titan.ccp.models.records.AggregatedPowerConsumptionRecord;

public class AggregatedPowerConsumptionRecordSerializer implements Serializer<AggregatedPowerConsumptionRecord> {

	private static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");
	private static final int BYTE_BUFFER_CAPACITY = 65536; // Is only virtual memory

	private final ByteBufferSerializer byteBufferSerializer = new ByteBufferSerializer();

	@Override
	public void configure(final Map<String, ?> configs, final boolean isKey) {
		this.byteBufferSerializer.configure(configs, isKey);
	}

	@Override
	public byte[] serialize(final String topic, final AggregatedPowerConsumptionRecord record) {
		final ByteBuffer buffer = ByteBuffer.allocateDirect(BYTE_BUFFER_CAPACITY);

		final byte[] identifierBytes = record.getIdentifier().getBytes(DEFAULT_CHARSET);
		buffer.putInt(identifierBytes.length);
		buffer.put(identifierBytes);
		buffer.putLong(record.getTimestamp());
		buffer.putInt(record.getMin());
		buffer.putInt(record.getMax());
		buffer.putLong(record.getCount());
		buffer.putLong(record.getSum());
		buffer.putDouble(record.getAverage());

		return this.byteBufferSerializer.serialize(topic, buffer);
	}

	@Override
	public void close() {
		this.byteBufferSerializer.close();
	}

}
