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

public class AvroMapper {
  
  /**
   * Creates a mapping function from a {@code Row} to an Avro object.
   * @param <T> The type of the Avro object.
   * @param factory A function for creating new Avro objects.
   * @return A new Avro object.
   */
  public static <T extends SpecificRecordBase> Function<Row, T> recordFactory(Supplier<T> factory) {
    
    // Prepare for getting Avro details
    AvroDataAdapter dataAdapter = new AvroDataAdapter();
    T template = factory.get();
    
    // Schema and Fields of Avro object
    Schema schema = template.getSchema();
    List<String> schemaFields = dataAdapter.getFieldNames(template);
    
    // For reading data of a schema.
    SpecificDatumReader<T> specificDatumReader = new SpecificDatumReader<T>(schema);
    
    
    // Function for mapping from a row to a Avro object
    return (row) -> {
      // Decoder for the row
      RowDecoder rowDecoder = new RowDecoder(row, schemaFields);
      
      //New Object
      T avro = factory.get();
      
      try {
        //Read in the data into the Avro object
        avro = specificDatumReader.read(avro, rowDecoder);
      } catch (IOException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
      
      return avro;
    };
  }
}
