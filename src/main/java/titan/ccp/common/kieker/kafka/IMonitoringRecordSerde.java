package titan.ccp.common.kieker.kafka;

import java.nio.ByteBuffer;
import java.util.Map;
import java.util.function.Supplier;
import kieker.common.record.IMonitoringRecord;
import kieker.common.record.factory.IRecordFactory;
import org.apache.kafka.common.serialization.ByteBufferDeserializer;
import org.apache.kafka.common.serialization.ByteBufferSerializer;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.serialization.Serializer;
import titan.ccp.common.kieker.RegistrylessBinaryValueDeserializer;
import titan.ccp.common.kieker.RegistrylessBinaryValueSerializer;

public final class IMonitoringRecordSerde {

  public static <T extends IMonitoringRecord> Serde<T> serde(
      final IRecordFactory<T> recordFactory) {
    return Serdes.serdeFrom(new IMonitoringRecordSerializer<T>(),
        new IMonitoringRecordDeserializer<>(recordFactory));
  }

  public static <T extends IMonitoringRecord> Serializer<T> serializer() {
    return new IMonitoringRecordSerializer<>();
  }

  public static <T extends IMonitoringRecord> Deserializer<T> deserializer(
      final IRecordFactory<T> recordFactory) {
    return new IMonitoringRecordDeserializer<>(recordFactory);
  }

  private static class IMonitoringRecordSerializer<T extends IMonitoringRecord>
      implements Serializer<T> {

    private static final int DEFAULT_BYTE_BUFFER_CAPACITY = 1024;

    private final ByteBufferSerializer byteBufferSerializer = new ByteBufferSerializer();

    private final Supplier<ByteBuffer> byteBufferFactory;

    public IMonitoringRecordSerializer() {
      this(() -> ByteBuffer.allocate(DEFAULT_BYTE_BUFFER_CAPACITY));
    }

    public IMonitoringRecordSerializer(final Supplier<ByteBuffer> byteBufferFactory) {
      this.byteBufferFactory = byteBufferFactory;
    }

    @Override
    public void configure(final Map<String, ?> configs, final boolean isKey) {
      this.byteBufferSerializer.configure(configs, isKey);
    }


    @Override
    public byte[] serialize(final String topic, final T record) {
      if (record == null) {
        return null;
      }
      final ByteBuffer buffer = this.byteBufferFactory.get();
      record.serialize(new RegistrylessBinaryValueSerializer(buffer));
      buffer.flip();
      return this.byteBufferSerializer.serialize(topic, buffer);
    }

    @Override
    public void close() {
      this.byteBufferSerializer.close();
    }

  }

  private static class IMonitoringRecordDeserializer<T extends IMonitoringRecord>
      implements Deserializer<T> {

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
      if (data == null) {
        return null;
      }
      final ByteBuffer buffer = this.byteBufferDeserializer.deserialize(topic, data);
      return this.recordFactory.create(new RegistrylessBinaryValueDeserializer(buffer));
    }

    @Override
    public void close() {
      this.byteBufferDeserializer.close();
    }

  }

}
