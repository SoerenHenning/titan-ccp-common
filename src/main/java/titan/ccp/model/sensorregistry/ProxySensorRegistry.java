package titan.ccp.model.sensorregistry;

import java.util.Collection;
import java.util.Optional;

// TODO need to handle synchronization?
public class ProxySensorRegistry implements SensorRegistry {

  private SensorRegistry backingSensorRegisty;

  public ProxySensorRegistry() {
    this.backingSensorRegisty = new DummySensorRegistry(); // TODO remove
  }

  public ProxySensorRegistry(final SensorRegistry backingSensorRegisty) {
    this.backingSensorRegisty = backingSensorRegisty;
  }

  @Override
  public Optional<MachineSensor> getSensorForIdentifier(final String identifier) {
    return this.backingSensorRegisty.getSensorForIdentifier(identifier);
  }

  @Override
  public AggregatedSensor getTopLevelSensor() {
    return this.backingSensorRegisty.getTopLevelSensor();
  }

  @Override
  public Collection<MachineSensor> getMachineSensors() {
    return this.backingSensorRegisty.getMachineSensors();
  }

  public void setBackingSensorRegisty(final SensorRegistry backingSensorRegisty) {
    this.backingSensorRegisty = backingSensorRegisty;
  }

  @Override
  public int hashCode() {
    return this.backingSensorRegisty.hashCode();
  }

  @Override
  public boolean equals(final Object obj) {
    return this.backingSensorRegisty.equals(obj);
  }

  @Override
  public Collection<Sensor> flat() {
    // TODO Auto-generated method stub
    return null;
  }

}
