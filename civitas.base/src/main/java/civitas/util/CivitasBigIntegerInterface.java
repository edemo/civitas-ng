package civitas.util;

import java.math.BigInteger;

import civitas.crypto.Constants;

public interface CivitasBigIntegerInterface extends Constants {

	BigInteger asBigint();

	default boolean isProbablePrime(final int certainty) {
		return asBigint().isProbablePrime(certainty);
	}

	default CivitasBigInteger nextProbablePrime() {
		return new CivitasBigInteger(asBigint().nextProbablePrime());
	}

	default CivitasBigInteger divide(final CivitasBigInteger q) {
		return new CivitasBigInteger(asBigint().divide(q.i));
	}

	default CivitasBigInteger modInverse(final CivitasBigInteger p) {
		return new CivitasBigInteger(asBigint().modInverse(p.i));
	}

	default CivitasBigInteger modNegate(final CivitasBigInteger p) {
		return new CivitasBigInteger(asBigint().negate().mod(p.i));
	}

	default CivitasBigInteger pow(final int j) {
		return new CivitasBigInteger(asBigint().pow(j));
	}
}
