package civitas.crypto.parameters;

import java.util.Map;

import civitas.crypto.CryptoException;
import civitas.util.CivitasBigInteger;

public class DecodeChoice {
	public int apply(Map<CivitasBigInteger, Integer> map, CivitasBigInteger m)
			throws CryptoException {
		Integer found = map.get(m);
		if (null == found)
			throw new CryptoException("Brute force decoding failed");
		return found;
	}

}
