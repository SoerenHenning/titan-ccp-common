package titan.ccp.common.cassandra;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.exceptions.InvalidQueryException;
import com.datastax.driver.core.exceptions.NoHostAvailableException;
import com.datastax.driver.core.schemabuilder.KeyspaceOptions;
import com.datastax.driver.core.schemabuilder.SchemaBuilder;
import com.google.common.collect.ImmutableMap;
import java.time.Duration;
import java.time.Instant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Builder for creating a Cassandra {@link Session}.
 */
public class SessionBuilder {

  private static final String CASSANDRA_DEFAULT_HOST = "localhost";
  private static final int CASSANDRA_DEFAULT_PORT = 9160;
  private static final long WAITING_SLEEP_MILLIS = 1_000;
  private static final int DEFAULT_TIMEOUT = 30_000;

  private static final Logger LOGGER = LoggerFactory.getLogger(SessionBuilder.class);

  private String contactPoint = CASSANDRA_DEFAULT_HOST; // NOPMD
  private int port = CASSANDRA_DEFAULT_PORT;// NOPMD
  private String keyspace; // null per default//NOPMD
  private int timeoutInMillis = DEFAULT_TIMEOUT;// NOPMD

  public SessionBuilder contactPoint(final String contactPoint) {
    this.contactPoint = contactPoint;
    return this;
  }

  public SessionBuilder port(final int port) {
    this.port = port;
    return this;
  }

  public SessionBuilder keyspace(final String keyspace) {
    this.keyspace = keyspace;
    return this;
  }

  public SessionBuilder timeoutInMillis(final int timeoutInMillis) {
    this.timeoutInMillis = timeoutInMillis;
    return this;
  }

  /**
   * Creates a Cassandra {@link Session} from the configuration of this {@link SessionBuilder}.
   *
   * @return The created Cassandra session wrapped in a {@link ClusterSession}.
   */
  public ClusterSession build() {
    final Instant start = Instant.now();
    if (this.keyspace == null) {
      LOGGER.info("Try to connect to Cassandra on '{}:{}' without keyspace.", this.contactPoint,
          this.port);
    } else {
      LOGGER.info("Try to connect to Cassandra on '{}:{}' with keyspace '{}'.", this.contactPoint,
          this.port, this.keyspace);
    }
    Cluster cluster = this.buildCluster();
    while (true) {
      try {
        final Session session = this.connectToCluster(cluster);
        return new ClusterSession(cluster, session);
      } catch (final NoHostAvailableException exception) {
        LOGGER.info("Host is not available");
        if (Duration.between(start, Instant.now()).toMillis() < this.timeoutInMillis) {
          LOGGER.info("Retry in {} milliseconds", WAITING_SLEEP_MILLIS);
          cluster.close();
          cluster = this.buildCluster();
          this.sleep();
        } else {
          LOGGER.error("Connection to could not be established and timeout exceeded.",
              this.contactPoint, this.port);
          throw exception;
        }
      } catch (final InvalidQueryException exception) {
        LOGGER.info("Keyspace '{}' does (probably) not exist.", this.keyspace);
        this.createKeyspaceIfNotExists(cluster);
      }
    }
  }

  private Cluster buildCluster() {
    return Cluster.builder().addContactPoint(this.contactPoint).withPort(this.port).build();
  }

  private Session connectToCluster(final Cluster cluster) {
    if (this.keyspace == null) {
      final Session session = cluster.connect();
      LOGGER.info("Succesfully connected to '{}:{}' without keyspace.", this.contactPoint,
          this.port);
      return session;
    } else {
      final Session session = cluster.connect(this.keyspace);
      LOGGER.info("Succesfully connected to '{}:{}' with keyspace '{}'.", this.contactPoint,
          this.port, this.keyspace);
      return session;
    }
  }

  private void createKeyspaceIfNotExists(final Cluster cluster) {
    if (this.keyspace != null) {
      final Session session = cluster.connect();
      final KeyspaceOptions statement = SchemaBuilder.createKeyspace(this.keyspace).ifNotExists()
          .with().replication(ImmutableMap.of("class", "SimpleStrategy", "replication_factor", 1));
      session.execute(statement);
      session.close();
      LOGGER.info("Keyspace '{}' created.", this.keyspace);
    }
  }

  private void sleep() {
    try {
      Thread.sleep(WAITING_SLEEP_MILLIS);
    } catch (final InterruptedException e) {
      throw new IllegalStateException(e);
    }
  }

  /**
   * Wrapping class for a Cassandra {@link Cluster} and {@link Session} since the {@link Session} is
   * not enough in some cases.
   */
  public static class ClusterSession {

    private final Cluster cluster;
    private final Session session;

    public ClusterSession(final Cluster cluster, final Session session) {
      this.cluster = cluster;
      this.session = session;
    }

    public Cluster getCluster() {
      return this.cluster;
    }

    public Session getSession() {
      return this.session;
    }

  }

}
