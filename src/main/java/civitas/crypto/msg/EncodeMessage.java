package civitas.crypto.msg;

import civitas.crypto.CryptoException;
import civitas.crypto.parameters.ElGamalParametersC;
import civitas.crypto.parameters.encoder.SchnorrPrimeEncoder;
import civitas.util.CivitasBigInteger;
import civitas.util.Use;

public class EncodeMessage {
	protected static final String CHARSET = "UTF-8";

	@Use
	SchnorrPrimeEncoder schnorrPrimeEncoder;

	public CivitasBigInteger apply(CivitasBigInteger plaintext,
			ElGamalParametersC params) throws CryptoException {
		CivitasBigInteger m = schnorrPrimeEncoder.apply(plaintext, params);
		return m;
	}

	public CivitasBigInteger apply(int i, ElGamalParametersC params)
			throws CryptoException {
		return apply(CivitasBigInteger.valueOf(i), params);
	}

	public CivitasBigInteger apply(String s, ElGamalParametersC params)
			throws CryptoException {
		try {
			return apply(new CivitasBigInteger(s.getBytes(CHARSET)), params);
		} catch (Exception e) {
			throw new CryptoException("I thought it is impossible");
		}
	}

}
