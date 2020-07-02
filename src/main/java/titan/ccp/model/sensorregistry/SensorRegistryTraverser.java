package titan.ccp.model.sensorregistry;

/**
 * Class for traversing a {@link SensorRegistry}. That is, it executes a given function for each
 * Sensor.
 */
public class SensorRegistryTraverser {

  public void traverseAggregated(final SensorRegistry registry,
      final SensorVisitor<AggregatedSensor> visitor) {
    this.traverseSensor(registry.getTopLevelSensor(), visitor);
  }

  private void traverseSensor(final AggregatedSensor sensor,
      final SensorVisitor<AggregatedSensor> visitor) {
    visitor.visit(sensor);
    final AggregatedSensor aggregatedSensor = sensor;
    for (final Sensor childSensor : aggregatedSensor.getChildren()) {
      if (childSensor instanceof AggregatedSensor) {
        this.traverseSensor((AggregatedSensor) childSensor, visitor);
      }
    }
  }

}
