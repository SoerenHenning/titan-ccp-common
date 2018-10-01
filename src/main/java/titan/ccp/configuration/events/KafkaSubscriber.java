package titan.ccp.configuration.events;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Implementation is currently not thread-safe. Once the {@code run()} method is
 * called, further calls to {@code subscribe()} will result in errors.
 *
 */
public class KafkaSubscriber {

	private static final Duration DEFAULT_POLL_TIMEOUT = Duration.ofSeconds(5);

	private static final Logger LOGGER = LoggerFactory.getLogger(KafkaSubscriber.class);

	private final KafkaConsumer<Event, String> consumer;

	private final String topicName;

	private final long pollTimeoutInMs;

	private final Map<Event, List<Consumer<String>>> subscriptions = new EnumMap<>(Event.class);

	private volatile boolean terminationRequested = false;
	private final CompletableFuture<Void> terminationRequestResult = new CompletableFuture<>();

	public KafkaSubscriber(final String bootstrapServers, final String groupId, final String topicName) {
		this(bootstrapServers, groupId, topicName, DEFAULT_POLL_TIMEOUT);
	}

	public KafkaSubscriber(final String bootstrapServers, final String groupId, final String topicName,
			final Duration pollTimeout) {
		final Properties properties = new Properties();
		properties.put("bootstrap.servers", bootstrapServers);
		properties.put("group.id", groupId);
		// properties.put("enable.auto.commit", this.enableAutoCommit);
		// properties.put("auto.commit.interval.ms", this.autoCommitIntervalMs);
		// properties.put("session.timeout.ms", this.sessionTimeoutMs);

		this.consumer = new KafkaConsumer<>(properties, EventSerde.deserializer(), new StringDeserializer());
		this.topicName = topicName;
		this.pollTimeoutInMs = pollTimeout.toMillis();
	}

	public void run() {
		new Thread(() -> {
			this.consumer.subscribe(Arrays.asList(this.topicName));

			while (!this.terminationRequested) {
				final ConsumerRecords<Event, String> records = this.consumer.poll(this.pollTimeoutInMs);
				for (final ConsumerRecord<Event, String> record : records) {
					for (final Consumer<String> subscription : this.subscriptions.getOrDefault(record.key(), Collections.emptyList())) {
						subscription.accept(record.value());
					}
				}
			}

			this.consumer.close();
			this.terminationRequestResult.complete(null);
		}).start();
		LOGGER.info("Started listening for configuration changes.");
	}

	public void subscribe(final Event event, final Consumer<String> action) {
		this.subscriptions.computeIfAbsent(event, x -> new ArrayList<>(4)).add(action);
	}

	public CompletableFuture<Void> requestTermination() {
		this.terminationRequested = true;
		return this.terminationRequestResult;
	}

}
