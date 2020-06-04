package titan.ccp.configuration.events;

/**
 * Interface representing something that can publish Events.
 */
public interface EventPublisher {

  void publish(final Event event, final String value);

  void close();

}
