package titan.ccp.common.kafka.utils;

import java.util.Collection;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import org.apache.kafka.clients.admin.Admin;
import org.apache.kafka.clients.admin.AdminClientConfig;
import titan.ccp.common.kafka.utils.internal.TopicsExistsWaiter;

/**
 * Utility functions for Kafka.
 */
public class AdminUtils implements AutoCloseable {

  private final Admin kafkaAdmin;
  private final boolean autoClose;

  /**
   * Create a new {@link AdminUtils} objects using the provided Kafka {@link Admin} object. When
   * using this constructor, the {@link Admin} object is not automatically closed when this
   * {@link AdminUtils} is closed.
   */
  public AdminUtils(final Admin kafkaAdmin) {
    this(kafkaAdmin, false);
  }

  private AdminUtils(final Admin kafkaAdmin, final boolean autoClose) {
    this.kafkaAdmin = kafkaAdmin;
    this.autoClose = autoClose;
  }

  /**
   * Returns a {@link CompletableFuture} which completes when all provided topics exist and fails
   * exceptionally if the provided topics do not exist after a fixed amount of retries. This method
   * used a retry policy from the reasonable defaults {@link RetryOptions}.
   */
  public CompletableFuture<Void> awaitTopicsExists(final Collection<String> topics) {
    return this.awaitTopicsExists(topics, new RetryOptions());
  }

  /**
   * Returns a {@link CompletableFuture} which completes when all provided topics exist and fails
   * exceptionally if the provided topics do not exist after a given amount of retries. The
   * {@link RetryOptions} to use can be configured.
   */
  public CompletableFuture<Void> awaitTopicsExists(
      final Collection<String> topics,
      final RetryOptions retryOptions) {
    return new TopicsExistsWaiter(this.kafkaAdmin, topics, retryOptions).awaitTopics();
  }

  @Override
  public void close() throws Exception {
    if (this.autoClose) {
      this.kafkaAdmin.close();
    }
  }

  /**
   * Create a new {@link AdminUtils} objects using the provided Kafka bootstrap servers. All
   * internal used resources are automatically closed when calling close.
   */
  public static AdminUtils fromBootstrapServers(final String bootstrapServers) {
    final Properties props = new Properties();
    props.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
    return new AdminUtils(Admin.create(props), true);
  }

  // TODO remove
  public static void main(final String[] args) {

    try (AdminUtils kafkaUtils = AdminUtils.fromBootstrapServers("localhost:9092")) {

      final Collection<String> topics = Set.of("input", "output");

      final CompletableFuture<Void> ft = kafkaUtils.awaitTopicsExists(topics);


      ft.join();
      System.out.println("Ready!!!");

      try {
        final Void x = ft.get();
        System.out.println(x);
      } catch (final InterruptedException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      } catch (final ExecutionException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }

    } catch (final Exception e1) {
      // TODO Auto-generated catch block
      e1.printStackTrace();
    }

  }

}
