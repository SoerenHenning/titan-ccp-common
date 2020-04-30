package titan.ccp.common.cassandra;

import com.datastax.driver.core.Row;
import java.io.IOException;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;
import org.apache.avro.Schema;
import org.apache.avro.specific.SpecificDatumReader;
import org.apache.avro.specific.SpecificRecordBase;
import titan.ccp.common.avro.cassandra.AvroDataAdapter;
import titan.ccp.common.avro.cassandra.RowDecoder;

public class AvroMapper<T extends SpecificRecordBase> implements Function<Row, T> {
  
  private final Supplier<T> factory;
  private final List<String> schemaFields;
  private final SpecificDatumReader<T> specificDatumReader;
  

  /**
   * Creates a new {@code AvroMapper} that contains a mapping function from a Cassandra {@code Row} to an Avro Object.
   * @param factory The function for creating a new avro Object.
   */
  public AvroMapper(Supplier<T> factory) {
    this.factory = factory;
    
    // Prepare for getting Avro details
    AvroDataAdapter dataAdapter = new AvroDataAdapter();
    T template = factory.get();
    
    // Schema and Fields of Avro object
    Schema schema = template.getSchema();
    schemaFields = dataAdapter.getFieldNames(template);
    
    // Creates later avro object
    specificDatumReader = new SpecificDatumReader<T>(schema);
  }
  
  /**
   * A mapping function from a {@code Row} to an Avro object.
   * @param row The cassandra {@code Row} where the function should be applied to.
   * @return A new Avro object.
   */
  @Override
  public T apply(Row row) {
    // Decoder for the row
    RowDecoder rowDecoder = new RowDecoder(row, schemaFields);
    
    //New Object
    T avro = factory.get();
    
    try {
      //Read in the data into the Avro object
      avro = specificDatumReader.read(avro, rowDecoder);
    } catch (IOException e) {
      throw new IllegalStateException("Could not map a cassandra row to an Avro object.", e);
    }
    
    return avro;
  }
}
