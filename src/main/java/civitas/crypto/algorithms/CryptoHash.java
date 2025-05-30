package civitas.crypto.algorithms;

import java.util.List;

import civitas.crypto.messagedigest.MessageDigest;
import civitas.crypto.messagedigest.ObtainMessageDigest;
import civitas.util.CivitasBigInteger;
import civitas.util.Use;

public class CryptoHash {

	@Use
	ObtainMessageDigest obtainMessageDigest;

	/**
	 * Compute a hash over a list of CivitasBigIntegers.
	 */
	public byte[] apply(List<CivitasBigInteger> l) {
		// Compute the hash by updating a message digest
		// with the byte representation of the big ints.
		MessageDigest md = obtainMessageDigest.apply();
		for (CivitasBigInteger i : l) {
			md.update(i.toByteArray());
		}
		return md.digest();
	}

	public CivitasBigInteger apply(CivitasBigInteger a, CivitasBigInteger b) {
		return apply(a, b, null);
	}

	public CivitasBigInteger apply(CivitasBigInteger a, CivitasBigInteger b,
			CivitasBigInteger c) {
		return apply(a, b, c, null);
	}

	public CivitasBigInteger apply(CivitasBigInteger a, CivitasBigInteger b,
			CivitasBigInteger c, byte[] d) {
		// Compute the hash by updating a message digest
		// with the byte representation of the big ints.
		MessageDigest md = obtainMessageDigest.apply();
		if (a != null)
			md.update(a.toByteArray());
		if (b != null)
			md.update(b.toByteArray());
		if (c != null)
			md.update(c.toByteArray());
		if (d != null)
			md.update(d);
		return new CivitasBigInteger(1, md.digest());
	}

}
