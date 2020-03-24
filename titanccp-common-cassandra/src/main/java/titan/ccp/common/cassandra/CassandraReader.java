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

public class CassandraReader {

//  public static <T extends SpecificRecordBase> Function<Row, T> recordFactory(Supplier<T> factory) {
//    
//    // Prepare for getting Avro details
//    AvroDataAdapter dataAdapter = new AvroDataAdapter();
//    T template = factory.get();
//    
//    // Schema and Fields of Avro type
//    Schema schema = template.getSchema();
//    List<String> schemaFields = dataAdapter.getFieldNames(template);
//    
//    // Function for mapping from a row to a Avro object
//    return (row) -> {
//      // The new Avro object
//      T avro = factory.get();
//      
//      // Decoder for the row
//      RowDecoder rowDecoder = new RowDecoder(row, schemaFields);
//      
//      try {
//        // Resolving decoder needed for the SpecificRedord
//        ResolvingDecoder resolvingDecoder = DecoderFactory.get().resolvingDecoder(schema, schema, rowDecoder);
//        avro.customDecode(resolvingDecoder);
//      } catch (IOException e) {
//        e.printStackTrace();
//      }
//      
//      return avro;
//    };
//  }
  
  public static <T extends SpecificRecordBase> Function<Row, T> recordFactory(Supplier<T> factory) {
    
    // Prepare for getting Avro details
    AvroDataAdapter dataAdapter = new AvroDataAdapter();
    T template = factory.get();
    
    // Schema and Fields of Avro type
    Schema schema = template.getSchema();
    List<String> schemaFields = dataAdapter.getFieldNames(template);
    
    SpecificDatumReader<T> specificDatumReader = new SpecificDatumReader<T>(schema);
    
    
    // Function for mapping from a row to a Avro object
    return (row) -> {
      // Decoder for the row
      RowDecoder rowDecoder = new RowDecoder(row, schemaFields);
      T avro = factory.get();;
      
      try {
         avro = specificDatumReader.read(avro, rowDecoder);
      } catch (IOException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
      
      return avro;
    };
  }
}
