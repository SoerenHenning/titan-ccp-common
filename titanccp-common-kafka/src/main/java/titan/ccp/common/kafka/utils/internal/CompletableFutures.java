package titan.ccp.common.kafka.utils.internal;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Backport Java 9 {@link CompletableFuture} factory methods.
 */
public final class CompletableFutures {

  // prefer this constructor with zero core threads for a shared pool,
  // to avoid blocking JVM exit
  private static final ScheduledExecutorService SCHEDULER = new ScheduledThreadPoolExecutor(0);

  private CompletableFutures() {}

  public static <U> CompletableFuture<U> failedFuture(Throwable ex) {
    final CompletableFuture<U> future = new CompletableFuture<>();
    future.completeExceptionally(ex);
    return future;
  }

  public static Executor delayedExecutor(long delay, TimeUnit unit) {
    return delayedExecutor(delay, unit, ForkJoinPool.commonPool());
  }

  public static Executor delayedExecutor(long delay, TimeUnit unit, Executor executor) {
    return r -> SCHEDULER.schedule(() -> executor.execute(r), delay, unit);
  }

}
