package civitas.crypto.algorithms;

import java.util.Base64;

import civitas.util.CivitasBigInteger;

public class ConvertToBigInt {

	public CivitasBigInteger apply(String s) {
		return new CivitasBigInteger(Base64.getDecoder().decode(s));
	}

}
