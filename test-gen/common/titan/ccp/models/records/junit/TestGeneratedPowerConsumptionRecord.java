package titan.ccp.models.records.junit;

import java.nio.ByteBuffer;

import org.junit.Assert;
import org.junit.Test;

import titan.ccp.models.records.PowerConsumptionRecord;
import kieker.common.util.registry.IRegistry;
import kieker.common.util.registry.Registry;

import kieker.test.common.junit.AbstractGeneratedKiekerTest;
import kieker.test.common.util.record.BookstoreOperationExecutionRecordFactory;
		
/**
 * Creates {@link OperationExecutionRecord}s via the available constructors and
 * checks the values passed values via getters.
 * 
 * @author Sören Henning
 * 
 * @since 
 */
public class TestGeneratedPowerConsumptionRecord extends AbstractGeneratedKiekerTest {

	public TestGeneratedPowerConsumptionRecord() {
		// empty default constructor
	}

	/**
	 * Tests {@link PowerConsumptionRecord#TestPowerConsumptionRecord(String, String, long, long, long, String, int, int)}.
	 */
	@Test
	public void testToArray() { // NOPMD (assert missing)
	for (int i=0;i<ARRAY_LENGTH;i++) {
			// initialize
			PowerConsumptionRecord record = new PowerConsumptionRecord(BYTE_VALUES.get(i % BYTE_VALUES.size()), LONG_VALUES.get(i % LONG_VALUES.size()), INT_VALUES.get(i % INT_VALUES.size()));
			
			// check values
			Assert.assertEquals("PowerConsumptionRecord.identifier values are not equal.", (byte) BYTE_VALUES.get(i % BYTE_VALUES.size()), record.getIdentifier());
			Assert.assertEquals("PowerConsumptionRecord.timestamp values are not equal.", (long) LONG_VALUES.get(i % LONG_VALUES.size()), record.getTimestamp());
			Assert.assertEquals("PowerConsumptionRecord.powerConsumptionInWh values are not equal.", (int) INT_VALUES.get(i % INT_VALUES.size()), record.getPowerConsumptionInWh());
			
			Object[] values = record.toArray();
			
			Assert.assertNotNull("Record array serialization failed. No values array returned.", values);
			Assert.assertEquals("Record array size does not match expected number of properties 3.", 3, values.length);
			
			// check all object values exist
			Assert.assertNotNull("Array value [0] of type Byte must be not null.", values[0]); 
			Assert.assertNotNull("Array value [1] of type Long must be not null.", values[1]); 
			Assert.assertNotNull("Array value [2] of type Integer must be not null.", values[2]); 
			
			// check all types
			Assert.assertTrue("Type of array value [0] " + values[0].getClass().getCanonicalName() + " does not match the desired type Byte", values[0] instanceof Byte);
			Assert.assertTrue("Type of array value [1] " + values[1].getClass().getCanonicalName() + " does not match the desired type Long", values[1] instanceof Long);
			Assert.assertTrue("Type of array value [2] " + values[2].getClass().getCanonicalName() + " does not match the desired type Integer", values[2] instanceof Integer);
								
			// check all object values 
			Assert.assertEquals("Array value [0] " + values[0] + " does not match the desired value " + BYTE_VALUES.get(i % BYTE_VALUES.size()),
				BYTE_VALUES.get(i % BYTE_VALUES.size()), values[0]
					);
			Assert.assertEquals("Array value [1] " + values[1] + " does not match the desired value " + LONG_VALUES.get(i % LONG_VALUES.size()),
				LONG_VALUES.get(i % LONG_VALUES.size()), values[1]
					);
			Assert.assertEquals("Array value [2] " + values[2] + " does not match the desired value " + INT_VALUES.get(i % INT_VALUES.size()),
				INT_VALUES.get(i % INT_VALUES.size()), values[2]
					);
		}
	}
	
	/**
	 * Tests {@link PowerConsumptionRecord#TestPowerConsumptionRecord(String, String, long, long, long, String, int, int)}.
	 */
	@Test
	public void testBuffer() { // NOPMD (assert missing)
		for (int i=0;i<ARRAY_LENGTH;i++) {
			// initialize
			PowerConsumptionRecord record = new PowerConsumptionRecord(BYTE_VALUES.get(i % BYTE_VALUES.size()), LONG_VALUES.get(i % LONG_VALUES.size()), INT_VALUES.get(i % INT_VALUES.size()));
			
			// check values
			Assert.assertEquals("PowerConsumptionRecord.identifier values are not equal.", (byte) BYTE_VALUES.get(i % BYTE_VALUES.size()), record.getIdentifier());
			Assert.assertEquals("PowerConsumptionRecord.timestamp values are not equal.", (long) LONG_VALUES.get(i % LONG_VALUES.size()), record.getTimestamp());
			Assert.assertEquals("PowerConsumptionRecord.powerConsumptionInWh values are not equal.", (int) INT_VALUES.get(i % INT_VALUES.size()), record.getPowerConsumptionInWh());
		}
	}
	
	/**
	 * Tests {@link PowerConsumptionRecord#TestPowerConsumptionRecord(String, String, long, long, long, String, int, int)}.
	 */
	@Test
	public void testParameterConstruction() { // NOPMD (assert missing)
		for (int i=0;i<ARRAY_LENGTH;i++) {
			// initialize
			PowerConsumptionRecord record = new PowerConsumptionRecord(BYTE_VALUES.get(i % BYTE_VALUES.size()), LONG_VALUES.get(i % LONG_VALUES.size()), INT_VALUES.get(i % INT_VALUES.size()));
			
			// check values
			Assert.assertEquals("PowerConsumptionRecord.identifier values are not equal.", (byte) BYTE_VALUES.get(i % BYTE_VALUES.size()), record.getIdentifier());
			Assert.assertEquals("PowerConsumptionRecord.timestamp values are not equal.", (long) LONG_VALUES.get(i % LONG_VALUES.size()), record.getTimestamp());
			Assert.assertEquals("PowerConsumptionRecord.powerConsumptionInWh values are not equal.", (int) INT_VALUES.get(i % INT_VALUES.size()), record.getPowerConsumptionInWh());
		}
	}
	
	@Test
	public void testEquality() {
		int i = 0;
		PowerConsumptionRecord oneRecord = new PowerConsumptionRecord(BYTE_VALUES.get(i % BYTE_VALUES.size()), LONG_VALUES.get(i % LONG_VALUES.size()), INT_VALUES.get(i % INT_VALUES.size()));
		i = 0;
		PowerConsumptionRecord copiedRecord = new PowerConsumptionRecord(BYTE_VALUES.get(i % BYTE_VALUES.size()), LONG_VALUES.get(i % LONG_VALUES.size()), INT_VALUES.get(i % INT_VALUES.size()));
		
		Assert.assertEquals(oneRecord, copiedRecord);
	}
	
	@Test
	public void testUnequality() {
		int i = 0;
		PowerConsumptionRecord oneRecord = new PowerConsumptionRecord(BYTE_VALUES.get(i % BYTE_VALUES.size()), LONG_VALUES.get(i % LONG_VALUES.size()), INT_VALUES.get(i % INT_VALUES.size()));
		i = 1;
		PowerConsumptionRecord anotherRecord = new PowerConsumptionRecord(BYTE_VALUES.get(i % BYTE_VALUES.size()), LONG_VALUES.get(i % LONG_VALUES.size()), INT_VALUES.get(i % INT_VALUES.size()));
		
		Assert.assertNotEquals(oneRecord, anotherRecord);
	}
}
