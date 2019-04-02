package titan.ccp.models.records;

import kieker.common.exception.RecordInstantiationException;
import kieker.common.record.factory.IRecordFactory;
import kieker.common.record.io.IValueDeserializer;

/**
 * @author Generic Kieker
 *
 * @since 1.14
 */
public final class ActivePowerRecordFactory implements IRecordFactory<ActivePowerRecord> {

  @Override
  public ActivePowerRecord create(final IValueDeserializer deserializer)
      throws RecordInstantiationException {
    return new ActivePowerRecord(deserializer);
  }

  @Override
  public String[] getValueNames() {
    return ActivePowerRecord.VALUE_NAMES; // NOPMD
  }

  @Override
  public Class<?>[] getValueTypes() {
    return ActivePowerRecord.TYPES; // NOPMD
  }

  @Override
  public int getRecordSizeInBytes() {
    return ActivePowerRecord.SIZE;
  }
}
