package titan.ccp.model.sensorregistry;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public interface AggregatedSensor extends Sensor {

  Collection<Sensor> getChildren();

  default Collection<MachineSensor> getAllChildren() {
    final List<MachineSensor> allChildren = new ArrayList<>();
    final Queue<Sensor> untraversedSensors = new LinkedList<>(this.getChildren());
    while (!untraversedSensors.isEmpty()) {
      final Sensor sensor = untraversedSensors.poll();
      if (sensor instanceof MachineSensor) {
        allChildren.add((MachineSensor) sensor);
      } else if (sensor instanceof AggregatedSensor) {
        untraversedSensors.addAll(((AggregatedSensor) sensor).getChildren());
      }
    }
    return allChildren;
  }

  /**
   * Flattens this {@link AggregatedSensor} and all of its children to a collection of
   * {@link Sensor}s.
   *
   * @return A collection containing this {@link AggregatedSensor} and all of its children.
   */
  default Collection<Sensor> flatten() {
    final List<Sensor> accumulator = new ArrayList<>();
    final Queue<Sensor> untraversedSensors = new LinkedList<>();
    untraversedSensors.add(this);
    while (!untraversedSensors.isEmpty()) {
      final Sensor sensor = untraversedSensors.poll();
      accumulator.add(sensor);
      if (sensor instanceof AggregatedSensor) {
        untraversedSensors.addAll(((AggregatedSensor) sensor).getChildren());
      }
    }
    return accumulator;
  }

}
