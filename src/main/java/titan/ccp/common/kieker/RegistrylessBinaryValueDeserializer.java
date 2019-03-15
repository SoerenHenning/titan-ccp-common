
package titan.ccp.common.kieker;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import kieker.common.exception.RecordInstantiationException;
import kieker.common.record.io.AbstractValueDeserializer;
import kieker.common.record.io.IValueDeserializer;

public class RegistrylessBinaryValueDeserializer extends AbstractValueDeserializer
    implements IValueDeserializer {

  private static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");

  private static final byte TRUE_VALUE = (byte) 1;

  private final ByteBuffer buffer;

  public RegistrylessBinaryValueDeserializer(final ByteBuffer buffer) {
    this.buffer = buffer;
  }

  @Override
  public boolean getBoolean() {
    return this.getByte() == RegistrylessBinaryValueDeserializer.TRUE_VALUE;
  }

  @Override
  public byte getByte() {
    return this.buffer.get();
  }

  @Override
  public int getInt() {
    return this.buffer.getInt();
  }

  @Override
  public long getLong() {
    return this.buffer.getLong();
  }

  @Override
  public double getDouble() {
    return this.buffer.getDouble();
  }

  @Override
  public String getString() {
    final int stringLength = this.buffer.getInt();
    final byte[] stringBytes = new byte[stringLength];
    this.buffer.get(stringBytes);
    return new String(stringBytes, DEFAULT_CHARSET);
  }

  @Override
  public <T extends Enum<T>> T getEnumeration(final Class<T> clazz)
      throws RecordInstantiationException {
    return this.enumerationValueOf(clazz, this.buffer.getInt());
  }

  @Override
  public char getChar() {
    return this.buffer.getChar();
  }

  @Override
  public short getShort() {
    return this.buffer.getShort();
  }

  @Override
  public float getFloat() {
    return this.buffer.getFloat();
  }

}
