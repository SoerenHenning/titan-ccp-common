package titan.ccp.model.sensorregistry;

import java.util.Optional;

public interface Sensor {

  Optional<AggregatedSensor> getParent();

  String getIdentifier();

  String getName();

}
