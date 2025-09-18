package civitas.util;

import java.math.BigInteger;

public class CivitasBigintegerBase implements CivitasBigIntegerInterface {

	public final BigInteger i;

	public CivitasBigintegerBase(final BigInteger i) {
		super();
		this.i = i;
	}

	public int bitLength() {
		return i.bitLength();
	}

	public int intValue() {
		return i.intValue();
	}

	public int compareTo(final CivitasBigInteger n) {
		return i.compareTo(n.i);
	}

	@Override
	public String toString() {
		return i.toString();
	}

	@Override
	public boolean equals(final Object o) {
		return (o instanceof CivitasBigInteger) && this.i.equals(((CivitasBigInteger) o).i);
	}

	@Override
	public int hashCode() {
		return i.hashCode();
	}

	public byte[] toByteArray() {
		return i.toByteArray();
	}

	@Override
	public BigInteger asBigint() {
		return i;
	}
}
