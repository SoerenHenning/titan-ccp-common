package titan.ccp.common.kafka.utils;

import java.time.Duration;

/**
 * Options for a retrying Kafka operation.
 */
public class RetryOptions {

  private static final int MAX_RETRIES_DEFAULT = 30;
  private static final Duration RETRY_DELAY_DEFAULT = Duration.ofSeconds(1);
  private static final Duration KAFKA_TIMEOUT_DEFAULT = Duration.ofSeconds(1);

  private int maxRetries = MAX_RETRIES_DEFAULT; // NOPMD
  private Duration retryDelay; // NOPMD
  private Duration kafkaTimeout; // NOPMD

  public int maxRetries() {
    return this.maxRetries;
  }

  public RetryOptions maxRetries(final int maxRetries) {
    this.maxRetries = maxRetries;
    return this;
  }

  public Duration retryDelay() {
    if (this.retryDelay == null) {
      return RETRY_DELAY_DEFAULT;
    }
    return this.retryDelay;
  }

  public RetryOptions retryDelay(final Duration retryDelay) {
    this.retryDelay = retryDelay;
    return this;
  }

  public Duration kafkaTimeout() {
    if (this.kafkaTimeout == null) {
      return KAFKA_TIMEOUT_DEFAULT;
    }
    return this.kafkaTimeout;
  }

  public RetryOptions kafkaTimeout(final Duration kafkaTimeout) {
    this.kafkaTimeout = kafkaTimeout;
    return this;
  }

}
