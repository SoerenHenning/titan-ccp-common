package titan.ccp.common.kieker.cassandra;

import com.datastax.driver.core.Row;

import kieker.common.exception.RecordInstantiationException;
import kieker.common.record.IMonitoringRecord;
import kieker.common.record.io.AbstractValueDeserializer;
import kieker.common.record.io.IValueDeserializer;

/**
 * A Kieker deserializer that creates a Kieker {@link IMonitoringRecord} from a
 * {@link Row} obtained from a Cassandra database.
 *
 * This class relies on the order of fields in a {@link Row}. Therefore, a
 * preceding {@code SELECT} statement needs to specify the columns and its order
 * explicitly.
 */
public class CassandraDeserializer extends AbstractValueDeserializer implements IValueDeserializer {

	private final Row row;

	private int lastIndex = -1;
	private final String[] columnNames;

	public CassandraDeserializer(final Row row, final String[] columnNames) {
		this.row = row;
		this.columnNames = columnNames;
	}

	@Override
	public boolean getBoolean() {
		return this.row.getBool(this.getCurrentColumnName());
	}

	@Override
	public byte getByte() {
		return this.row.getByte(this.getCurrentColumnName());
	}

	@Override
	public char getChar() {
		final char[] chars = this.row.getString(this.getCurrentColumnName()).toCharArray();
		if (chars.length == 0) {
			throw new IllegalStateException();
		}
		return chars[0];
	}

	@Override
	public short getShort() {
		return this.row.getShort(this.getCurrentColumnName());
	}

	@Override
	public int getInt() {
		return this.row.getInt(this.getCurrentColumnName());
	}

	@Override
	public long getLong() {
		return this.row.getLong(this.getCurrentColumnName());
	}

	@Override
	public float getFloat() {
		return this.row.getFloat(this.getCurrentColumnName());
	}

	@Override
	public double getDouble() {
		return this.row.getDouble(this.getCurrentColumnName());
	}

	@Override
	public String getString() {
		return this.row.getString(this.getCurrentColumnName());
	}

	@Override
	public <T extends Enum<T>> T getEnumeration(final Class<T> clazz) throws RecordInstantiationException {
		// Depend on array serialization strategy
		return super.enumerationValueOf(clazz, this.row.getInt(this.getCurrentColumnName()));
	}

	@Override
	public byte[] getBytes(final byte[] target) {
		this.row.getBytes(this.getCurrentColumnName()).get(target);
		return target;
	}

	private String getCurrentColumnName() {
		this.lastIndex++;
		return this.columnNames[this.lastIndex];
	}

}
