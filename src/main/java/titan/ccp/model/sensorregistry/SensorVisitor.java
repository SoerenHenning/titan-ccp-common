package titan.ccp.model.sensorregistry;

/**
 * Interface for a function to be executed for each {@link Sensor} when traversed.
 *
 * @param <T> type of {@link Sensor} to be visited.
 */
@FunctionalInterface
public interface SensorVisitor<T extends Sensor> {

  void visit(T sensor);

}
