package titan.ccp.common.kieker.kafka;

import java.nio.ByteBuffer;
import java.util.Map;

import org.apache.kafka.common.serialization.ByteBufferDeserializer;
import org.apache.kafka.common.serialization.ByteBufferSerializer;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.serialization.Serializer;

import kieker.common.record.IMonitoringRecord;
import kieker.common.record.factory.IRecordFactory;
import titan.ccp.common.kieker.RegistrylessBinaryValueDeserializer;
import titan.ccp.common.kieker.RegistrylessBinaryValueSerializer;

public final class IMonitoringRecordSerde {

	public static <T extends IMonitoringRecord> Serde<T> serde(final IRecordFactory<T> recordFactory) {
		return Serdes.serdeFrom(new IMonitoringRecordSerializer<T>(), new IMonitoringRecordDeserializer<>(recordFactory));
	}

	public static <T extends IMonitoringRecord> Serializer<T> serializer() {
		return new IMonitoringRecordSerializer<>();
	}

	public static <T extends IMonitoringRecord> Deserializer<T> deserializer(final IRecordFactory<T> recordFactory) {
		return new IMonitoringRecordDeserializer<>(recordFactory);
	}

	private static class IMonitoringRecordSerializer<T extends IMonitoringRecord> implements Serializer<T> {

		private static final int BYTE_BUFFER_CAPACITY = 65536; // Is only virtual memory

		private final ByteBufferSerializer byteBufferSerializer = new ByteBufferSerializer();

		@Override
		public void configure(final Map<String, ?> configs, final boolean isKey) {
			this.byteBufferSerializer.configure(configs, isKey);
		}

		@Override
		public byte[] serialize(final String topic, final T record) {
			final ByteBuffer buffer = ByteBuffer.allocateDirect(BYTE_BUFFER_CAPACITY);
			record.serialize(new RegistrylessBinaryValueSerializer(buffer));
			return this.byteBufferSerializer.serialize(topic, buffer);
		}

		@Override
		public void close() {
			this.byteBufferSerializer.close();
		}

	}

	private static class IMonitoringRecordDeserializer<T extends IMonitoringRecord> implements Deserializer<T> {

		private final ByteBufferDeserializer byteBufferDeserializer = new ByteBufferDeserializer();

		private final IRecordFactory<T> recordFactory;

		public IMonitoringRecordDeserializer(final IRecordFactory<T> recordFactory) {
			this.recordFactory = recordFactory;
		}

		@Override
		public void configure(final Map<String, ?> configs, final boolean isKey) {
			this.byteBufferDeserializer.configure(configs, isKey);
		}

		@Override
		public T deserialize(final String topic, final byte[] data) {
			final ByteBuffer buffer = this.byteBufferDeserializer.deserialize(topic, data);
			return this.recordFactory.create(new RegistrylessBinaryValueDeserializer(buffer));
		}

		@Override
		public void close() {
			this.byteBufferDeserializer.close();
		}

	}
}
