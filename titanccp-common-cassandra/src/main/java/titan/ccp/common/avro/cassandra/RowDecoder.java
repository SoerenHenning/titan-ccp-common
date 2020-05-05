package titan.ccp.common.avro.cassandra;

import com.datastax.driver.core.Row;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.List;
import org.apache.avro.io.Decoder;
import org.apache.avro.util.Utf8;

/**
 * Decoder for de-serializing Avro values from a cassandra {@code Row}. The class uses the column
 * order from the target Avro schema and uses the fact that the {@code Row} contains the values with
 * the same name.
 *
 */
public class RowDecoder extends Decoder { // NOPMD

  private final Row row;
  private int lastIndex = -1;
  private final List<String> columnNames;

  /**
   * Create an Avro {@link Decoder} for the given row and columns.
   */
  public RowDecoder(final Row row, final List<String> columnNames) {
    super();
    this.row = row;
    this.columnNames = columnNames;
  }

  @Override
  public void readNull() throws IOException {
    throw new UnsupportedOperationException("");
  }

  @Override
  public boolean readBoolean() throws IOException {
    return this.row.getBool(this.getCurrentColumnName());
  }

  @Override
  public int readInt() throws IOException {
    return this.row.getInt(this.getCurrentColumnName());
  }

  @Override
  public long readLong() throws IOException {
    return this.row.getLong(this.getCurrentColumnName());
  }

  @Override
  public float readFloat() throws IOException {
    return this.row.getFloat(this.getCurrentColumnName());
  }

  @Override
  public double readDouble() throws IOException {
    return this.row.getDouble(this.getCurrentColumnName());
  }

  @Override
  public Utf8 readString(final Utf8 old) throws IOException {
    return new Utf8(this.row.getString(this.getCurrentColumnName()));
  }

  @Override
  public String readString() throws IOException {
    return this.row.getString(this.getCurrentColumnName());
  }

  @Override
  public void skipString() throws IOException {
    this.row.getString(this.getCurrentColumnName());
  }

  @Override
  public ByteBuffer readBytes(final ByteBuffer old) throws IOException {
    return this.row.getBytes(this.getCurrentColumnName());
  }

  @Override
  public void skipBytes() throws IOException {
    this.row.getBytes(this.getCurrentColumnName());
  }

  @Override
  public void readFixed(final byte[] bytes, final int start, final int length) throws IOException {
    throw new UnsupportedOperationException("");
  }

  @Override
  public void skipFixed(final int length) throws IOException {
    throw new UnsupportedOperationException("");
  }

  @Override
  public int readEnum() throws IOException {
    throw new UnsupportedOperationException("");
  }

  @Override
  public long readArrayStart() throws IOException {
    throw new UnsupportedOperationException("");
  }

  @Override
  public long arrayNext() throws IOException {
    throw new UnsupportedOperationException("");
  }

  @Override
  public long skipArray() throws IOException {
    throw new UnsupportedOperationException("");
  }

  @Override
  public long readMapStart() throws IOException {
    throw new UnsupportedOperationException("");
  }

  @Override
  public long mapNext() throws IOException {
    throw new UnsupportedOperationException("");
  }

  @Override
  public long skipMap() throws IOException {
    throw new UnsupportedOperationException("");
  }

  @Override
  public int readIndex() throws IOException {
    throw new UnsupportedOperationException("");
  }

  private String getCurrentColumnName() {
    this.lastIndex += 1;
    return this.columnNames.get(this.lastIndex);
  }
}
