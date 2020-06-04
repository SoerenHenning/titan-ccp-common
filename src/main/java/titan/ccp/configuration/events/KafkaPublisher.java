package titan.ccp.configuration.events;

import java.util.Properties;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

/**
 * {@link EventPublisher} for publishing to Kafka.
 */
public class KafkaPublisher implements EventPublisher {

  private final String topic;

  private final Producer<Event, String> producer;

  /**
   * Creates a new {@link KafkaPublisher} for the given bootstrap server and topic.
   */
  public KafkaPublisher(final String bootstrapServers, final String topic) {
    this(bootstrapServers, topic, new Properties());
  }

  /**
   * Creates a new {@link KafkaPublisher} for the given bootstrap server, topic and additional
   * default Kafka producer properties.
   */
  public KafkaPublisher(final String bootstrapServers, final String topic,
      final Properties defaultProperties) {
    this.topic = topic;

    final Properties properties = new Properties();
    properties.putAll(defaultProperties);
    properties.put("bootstrap.servers", bootstrapServers);
    // properties.put("acks", this.acknowledges);
    // properties.put("batch.size", this.batchSize);
    // properties.put("linger.ms", this.lingerMs);
    // properties.put("buffer.memory", this.bufferMemory);

    this.producer =
        new KafkaProducer<>(properties, EventSerde.serializer(), new StringSerializer());
  }

  @Override
  public void publish(final Event event, final String value) {
    final ProducerRecord<Event, String> record = new ProducerRecord<>(this.topic, event, value);
    this.producer.send(record);
  }

  @Override
  public void close() {
    this.producer.close();
  }

}
