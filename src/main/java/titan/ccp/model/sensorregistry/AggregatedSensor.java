package titan.ccp.model.sensorregistry;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public interface AggregatedSensor extends Sensor {

  public Collection<Sensor> getChildren();

  public default Collection<MachineSensor> getAllChildren() {
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

}
