package titan.ccp.model.sensorregistry;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import titan.ccp.model.sensorregistry.serialization.AggregatedSensorSerializer;
import titan.ccp.model.sensorregistry.serialization.MachineSensorSerializer;
import titan.ccp.model.sensorregistry.serialization.SensorRegistrySerializer;

public class MutableSensorRegistry implements SensorRegistry {

  private static final Gson GSON = new GsonBuilder()
      .registerTypeAdapter(MutableSensorRegistry.class, new SensorRegistrySerializer())
      .registerTypeAdapter(MutableAggregatedSensor.class, new AggregatedSensorSerializer())
      .registerTypeAdapter(MutableMachineSensor.class, new MachineSensorSerializer()).create();

  // TODO HashMap for efficient access to machine sensors
  private final Map<String, MutableMachineSensor> machineSensors = new HashMap<>();

  // TODO maybe access to root
  private final MutableAggregatedSensor topLevelSensor;

  public MutableSensorRegistry(final String topLevelSensorIdentifier) {
    this(topLevelSensorIdentifier, "");
  }

  public MutableSensorRegistry(final String topLevelSensorIdentifier,
      final String topLevelSensorName) {
    this.topLevelSensor =
        new MutableAggregatedSensor(this, topLevelSensorIdentifier, topLevelSensorName);
  }

  @Override
  public Optional<MachineSensor> getSensorForIdentifier(final String identifier) {
    return Optional.ofNullable(this.machineSensors.get(identifier));
  }

  @Override
  public MutableAggregatedSensor getTopLevelSensor() {
    return this.topLevelSensor;
  }

  @Override
  public Collection<MachineSensor> getMachineSensors() {
    return Collections.unmodifiableCollection(this.machineSensors.values());
  }

  protected boolean register(final MutableMachineSensor machineSensor) {
    final Object oldValue =
        this.machineSensors.putIfAbsent(machineSensor.getIdentifier(), machineSensor);
    return oldValue == null;
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(this.topLevelSensor);
  }

  @Override
  public boolean equals(final Object obj) {
    if (obj == this) {
      return true;
    }
    if (obj instanceof SensorRegistry) {
      final SensorRegistry other = (SensorRegistry) obj;
      return Objects.equals(this.getTopLevelSensor(), other.getTopLevelSensor());
    }
    return false;
  }

  @Override
  public String toJson() {
    return GSON.toJson(this);
  }

  @Override
  public Collection<Sensor> flat() {
    // TODO Auto-generated method stub
    return null;
  }

}
