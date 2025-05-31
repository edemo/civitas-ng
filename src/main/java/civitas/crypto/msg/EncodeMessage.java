package civitas.crypto.msg;

import civitas.crypto.CryptoException;
import civitas.crypto.parameters.ElGamalParameters;
import civitas.crypto.parameters.encoder.SchnorrPrimeEncode;
import civitas.util.CivitasBigInteger;
import civitas.util.Use;

public class EncodeMessage {
	protected static final String CHARSET = "UTF-8";

	@Use
	SchnorrPrimeEncode schnorrPrimeEncoder;

	public CivitasBigInteger apply(CivitasBigInteger plaintext,
			ElGamalParameters params) throws CryptoException {
		CivitasBigInteger m = schnorrPrimeEncoder.apply(plaintext, params);
		return m;
	}

	public CivitasBigInteger apply(int i, ElGamalParameters params)
			throws CryptoException {
		return apply(CivitasBigInteger.valueOf(i), params);
	}

	public CivitasBigInteger apply(String s, ElGamalParameters params)
			throws CryptoException {
		try {
			return apply(new CivitasBigInteger(s.getBytes(CHARSET)), params);
		} catch (Exception e) {
			throw new CryptoException("I thought it is impossible");
		}
	}

}
