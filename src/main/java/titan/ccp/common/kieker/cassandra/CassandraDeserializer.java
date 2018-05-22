package titan.ccp.common.kieker.cassandra;

import com.datastax.driver.core.Row;

import kieker.common.exception.RecordInstantiationException;
import kieker.common.record.io.AbstractValueDeserializer;
import kieker.common.record.io.IValueDeserializer;

public class CassandraDeserializer extends AbstractValueDeserializer implements IValueDeserializer {

	private final Row row;

	private int lastIndex = 0;

	public CassandraDeserializer(final Row row) {
		this.row = row;
	}

	@Override
	public boolean getBoolean() {
		this.lastIndex++;
		return this.row.getBool(this.lastIndex);
	}

	@Override
	public byte getByte() {
		this.lastIndex++;
		return this.row.getByte(this.lastIndex);
	}

	@Override
	public char getChar() {
		this.lastIndex++;
		final char[] chars = this.row.getString(this.lastIndex).toCharArray();
		if (chars.length == 0) {
			throw new IllegalStateException();
		}
		return chars[0];
	}

	@Override
	public short getShort() {
		this.lastIndex++;
		return this.row.getShort(this.lastIndex);
	}

	@Override
	public int getInt() {
		this.lastIndex++;
		return this.row.getInt(this.lastIndex);
	}

	@Override
	public long getLong() {
		this.lastIndex++;
		return this.row.getLong(this.lastIndex);
	}

	@Override
	public float getFloat() {
		this.lastIndex++;
		return this.row.getFloat(this.lastIndex);
	}

	@Override
	public double getDouble() {
		this.lastIndex++;
		return this.row.getDouble(this.lastIndex);
	}

	@Override
	public String getString() {
		this.lastIndex++;
		return this.row.getString(this.lastIndex);
	}

	@Override
	public <T extends Enum<T>> T getEnumeration(final Class<T> clazz) throws RecordInstantiationException {
		this.lastIndex++;
		// Depend on array serialization strategy
		return super.enumerationValueOf(clazz, this.row.getInt(this.lastIndex));
	}

	@Override
	public byte[] getBytes(final byte[] target) {
		this.lastIndex++;
		this.row.getBytes(this.lastIndex).get(target);
		return target;
	}

}
