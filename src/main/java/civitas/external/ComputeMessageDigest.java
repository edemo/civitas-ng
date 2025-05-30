package civitas.external;

import civitas.crypto.CryptoError;
import civitas.crypto.MessageDigest;
import civitas.util.Use;

public class ComputeMessageDigest {
	@Use
	ObtainMessageDigest obtainMessageDigest;

	public byte[] apply(byte[] a) throws CryptoError {
		MessageDigest md = obtainMessageDigest.apply();
		md.update(a);
		return md.digest();
	}

	public byte[] apply(byte[] a, int i) {
		MessageDigest md = obtainMessageDigest.apply();
		md.update(a);
		md.update(i);
		return md.digest();
	}

	public byte[] apply(String s) {
		MessageDigest md = obtainMessageDigest.apply();
		md.update(s);
		return md.digest();
	}

}
