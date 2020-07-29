package titan.ccp.common.kafka.utils.internal;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import org.apache.kafka.clients.admin.Admin;
import org.apache.kafka.clients.admin.DescribeTopicsOptions;
import org.apache.kafka.clients.admin.TopicDescription;
import org.apache.kafka.common.KafkaFuture;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import titan.ccp.common.kafka.utils.KafkaFutures;
import titan.ccp.common.kafka.utils.RetryOptions;

/**
 * Allows to wait for a Kafka topic to exist.
 */
public class TopicsExistsWaiter {

  private static final Logger LOGGER = LoggerFactory.getLogger(TopicsExistsWaiter.class);

  private final Admin kafkaAdmin;
  private final Collection<String> topics;
  private final RetryOptions retryOptions;

  /**
   * Create a {@link TopicsExistsWaiter} based on the given parameters.
   */
  public TopicsExistsWaiter(
      final Admin kafkaAdmin,
      final Collection<String> topics,
      final RetryOptions retryOptions) {
    this.kafkaAdmin = kafkaAdmin;
    this.topics = topics;
    this.retryOptions = retryOptions;
  }

  /**
   * Returns a void {@link CompletableFuture} which completes when all provided topics exist and
   * fails exceptionally if the provided topics do not exist after a fixed amount of retries.
   */
  public CompletableFuture<Void> awaitTopics() {
    return this.doCheck()
        // mimic missing exceptionallyAsync() from JDK 12
        .thenApply(CompletableFuture::completedFuture)
        .exceptionally(t -> {
          LOGGER.info("Topics '{}' do not exists.", this.topics, t); // NOCS // NPMD
          return this.retry(t, 0);
        })
        .thenCompose(Function.identity());
  }

  private CompletableFuture<Void> retry(final Throwable throwable, final int retry) {
    if (retry >= this.retryOptions.maxRetries()) {
      return CompletableFuture.failedFuture(throwable);
    }
    return CompletableFuture
        .supplyAsync(
            () -> this.doCheck(),
            this.buildExecutor())
        .thenCompose(Function.identity())
        // mimic missing exceptionallyAsync() from JDK 12
        .thenApply(CompletableFuture::completedFuture)
        .exceptionally(t -> {
          LOGGER.info("Topics '{}' do not exists.", this.topics, t); // NOCS // NPMD
          t.addSuppressed(throwable);
          return this.retry(t, retry + 1);
        })
        .thenCompose(Function.identity());
  }

  private CompletableFuture<Void> doCheck() {
    final KafkaFuture<Map<String, TopicDescription>> topicsDescriptions = this.kafkaAdmin
        .describeTopics(this.topics, this.buildDescribeTopicsOptions())
        .all();
    return KafkaFutures.toCompletableFuture(topicsDescriptions).<Void>thenApply(m -> null);
  }

  private Executor buildExecutor() {
    return CompletableFuture.delayedExecutor(
        this.retryOptions.retryDelay().toMillis(),
        TimeUnit.MILLISECONDS);
  }

  private DescribeTopicsOptions buildDescribeTopicsOptions() {
    return new DescribeTopicsOptions().timeoutMs((int) this.retryOptions.kafkaTimeout().toMillis());
  }

}
