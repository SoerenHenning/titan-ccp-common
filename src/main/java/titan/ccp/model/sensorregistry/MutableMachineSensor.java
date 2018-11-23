package titan.ccp.model.sensorregistry;

import java.util.Objects;

// Not really mutable but belongs to the others, consider renaming
public class MutableMachineSensor extends AbstractSensor implements MachineSensor {

  protected MutableMachineSensor(final AggregatedSensor parent, final String identifier,
      final String name) {
    super(parent, identifier, name);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(this.getIdentifier());
  }

  @Override
  public boolean equals(final Object obj) {
    if (obj == this) {
      return true;
    }
    if (obj instanceof MachineSensor) {
      final MachineSensor other = (MachineSensor) obj;
      return Objects.equals(this.getIdentifier(), other.getIdentifier());
    }
    return false;
  }

  @Override
  public String toString() {
    return this.getName() + '[' + this.getIdentifier() + ']';
  }

}
