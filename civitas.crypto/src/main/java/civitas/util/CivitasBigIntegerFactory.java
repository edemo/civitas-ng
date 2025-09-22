package civitas.util;

import java.math.BigInteger;
import java.util.Random;

public interface CivitasBigIntegerFactory {
	static CivitasBigInteger obtain(final BigInteger i) {
		return new CivitasBigInteger(i);
	}

	static CivitasBigInteger obtain(final byte[] bytes) {
		return new CivitasBigInteger(new BigInteger(bytes));
	}

	static CivitasBigInteger obtain(final int signum, final byte[] magnitude) {
		return new CivitasBigInteger(new BigInteger(signum, magnitude));
	}

	static CivitasBigInteger obtain(final int i, final Random random) {
		return new CivitasBigInteger(new BigInteger(i, random));
	}

	static CivitasBigInteger obtain(final int i) {
		return new CivitasBigInteger(BigInteger.valueOf(i));
	}
}
