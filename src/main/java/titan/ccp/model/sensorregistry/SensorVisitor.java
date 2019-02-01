package titan.ccp.model.sensorregistry;

@FunctionalInterface
public interface SensorVisitor<T extends Sensor> {

  public void visit(T sensor);

}
