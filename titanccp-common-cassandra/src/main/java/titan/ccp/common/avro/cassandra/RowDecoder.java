package titan.ccp.common.avro.cassandra;

import com.datastax.driver.core.Row;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.List;
import org.apache.avro.io.Decoder;
import org.apache.avro.util.Utf8;

/**
 * Decoder for de-serializing Avro values from a cassandra {@code Row}.
 * The class uses the column order from the target Avro schema and uses the fact
 * that the {@code Row} contains the values with the same name.
 * @author bjoern
 *
 */
public class RowDecoder extends Decoder {
  
  private final Row row;
  private int lastIndex = -1;
  private final List<String> columnNames;
  
  public RowDecoder(Row row, List<String> columnNames) {
    this.row = row;
    this.columnNames = columnNames;
  }

  @Override
  public void readNull() throws IOException {
    throw new UnsupportedOperationException("");
  }

  @Override
  public boolean readBoolean() throws IOException {
    return row.getBool(getCurrentColumnName());
  }

  @Override
  public int readInt() throws IOException {
    return row.getInt(getCurrentColumnName());
  }

  @Override
  public long readLong() throws IOException {
    return row.getLong(getCurrentColumnName());
  }

  @Override
  public float readFloat() throws IOException {
    return row.getFloat(getCurrentColumnName());
  }

  @Override
  public double readDouble() throws IOException {
    return row.getDouble(getCurrentColumnName());
  }

  @Override
  public Utf8 readString(Utf8 old) throws IOException {
    return new Utf8(row.getString(getCurrentColumnName()));
  }

  @Override
  public String readString() throws IOException {
    return row.getString(getCurrentColumnName());
  }

  @Override
  public void skipString() throws IOException {
    row.getString(getCurrentColumnName());
  }

  @Override
  public ByteBuffer readBytes(ByteBuffer old) throws IOException {
    return row.getBytes(getCurrentColumnName());
  }

  @Override
  public void skipBytes() throws IOException {
    row.getBytes(getCurrentColumnName());
  }

  @Override
  public void readFixed(byte[] bytes, int start, int length) throws IOException {
    throw new UnsupportedOperationException("");
  }

  @Override
  public void skipFixed(int length) throws IOException {
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
    return this.columnNames.get(lastIndex);
  }
}
