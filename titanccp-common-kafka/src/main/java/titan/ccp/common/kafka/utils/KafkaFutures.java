package titan.ccp.common.kafka.utils;

import java.util.concurrent.CompletableFuture;
import org.apache.kafka.common.KafkaFuture;

/**
 * Utility functions for {@link KafkaFuture}s.
 */
public final class KafkaFutures {

  private KafkaFutures() {}

  /**
   * Convert a {@link KafkaFuture} to a native Java {@link CompletableFuture}.
   */
  public static <T> CompletableFuture<T> toCompletableFuture(final KafkaFuture<T> kafkaFuture) {
    final CompletableFuture<T> wrappingFuture = new CompletableFuture<>();
    kafkaFuture.whenComplete((value, throwable) -> {
      if (throwable != null) {
        wrappingFuture.completeExceptionally(throwable);
      } else {
        wrappingFuture.complete(value);
      }
    });
    return wrappingFuture;
  }

}
