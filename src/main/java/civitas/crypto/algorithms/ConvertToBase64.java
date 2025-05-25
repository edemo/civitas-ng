package civitas.crypto.algorithms;

import java.util.Base64;

import civitas.util.CivitasBigInteger;

public class ConvertToBase64 {
	public String apply(byte[] a) {
		return Base64.getEncoder().encodeToString(a);
	}

	public String apply(CivitasBigInteger i) {
		return Base64.getEncoder().encodeToString(i.toByteArray());
	}

}
