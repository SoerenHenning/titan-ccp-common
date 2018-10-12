package titan.ccp.common.kieker.kafka;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.nio.BufferOverflowException;
import java.nio.ByteBuffer;

import org.apache.commons.lang3.StringUtils;
import org.apache.kafka.common.serialization.Serializer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import kieker.common.record.IMonitoringRecord;
import titan.ccp.common.kieker.RegistrylessBinaryValueSerializer;
import titan.ccp.models.records.ActivePowerRecord;

public class IMonitoringRecordSerdeTest {

	private Serializer<IMonitoringRecord> serializer;

	@Before
	public void setUp() throws Exception {
		this.serializer = IMonitoringRecordSerde.serializer();
	}

	@After
	public void tearDown() throws Exception {
		this.serializer = null;
	}

	@Test
	public void testSerializeByteArraySize() {
		final IMonitoringRecord record = new ActivePowerRecord("identifier", 12345, 42);
		final byte[] array = this.serializer.serialize("", record);

		final ByteBuffer kiekerBuffer = ByteBuffer.allocate(1024);
		record.serialize(new RegistrylessBinaryValueSerializer(kiekerBuffer));
		final int bytesWritten = kiekerBuffer.position();
		assertEquals(bytesWritten, array.length);

	}

	@Test
	public void testSerializeForTooLargeRecord() {
		final String identifier = StringUtils.repeat('x', 1024);
		final IMonitoringRecord record = new ActivePowerRecord(identifier, 12345, 42);

		final ByteBuffer kiekerBuffer = ByteBuffer.allocate(2048);
		record.serialize(new RegistrylessBinaryValueSerializer(kiekerBuffer));
		final int bytesWritten = kiekerBuffer.position();
		assertTrue(bytesWritten > 1024);

		assertThrows(BufferOverflowException.class, () -> this.serializer.serialize("", record));

	}

}
