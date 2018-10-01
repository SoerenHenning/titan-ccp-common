package titan.ccp.model.sensorregistry;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class MutableAggregatedSensor extends AbstractSensor implements AggregatedSensor {

  private final List<Sensor> children = new ArrayList<>();
  private final MutableSensorRegistry sensorRegistry;

  protected MutableAggregatedSensor(final MutableSensorRegistry registry, final String identifier,
      final String name) {
    super(null, identifier, name);
    this.sensorRegistry = registry;
  }

  protected MutableAggregatedSensor(final MutableAggregatedSensor parent, final String identifier,
      final String name) {
    super(parent, identifier, name);
    this.sensorRegistry = parent.sensorRegistry;
  }

  @Override
  public Collection<Sensor> getChildren() {
    return this.children;
  }

  public MutableAggregatedSensor addChildAggregatedSensor(final String identifier) {
    return this.addChildAggregatedSensor(identifier, "");
  }

  public MutableAggregatedSensor addChildAggregatedSensor(final String identifier,
      final String name) {
    final MutableAggregatedSensor aggregatedSensor =
        new MutableAggregatedSensor(this, identifier, name);
    this.children.add(aggregatedSensor);
    return aggregatedSensor;
  }

  public MachineSensor addChildMachineSensor(final String identifier) {
    return this.addChildMachineSensor(identifier, "");
  }

  public MachineSensor addChildMachineSensor(final String identifier, final String name) {
    final MutableMachineSensor machineSensor = new MutableMachineSensor(this, identifier, name);
    final boolean registerResult = this.sensorRegistry.register(machineSensor);
    if (!registerResult) {
      throw new IllegalArgumentException(
          "Sensor width identifier " + identifier + " is already registered.");
    }
    this.children.add(machineSensor);
    return machineSensor;
  }

  @Override
  public String toString() {
    return this.getName() + '[' + this.getIdentifier() + "] (" + this.children.size()
        + " children)";
  }

}
