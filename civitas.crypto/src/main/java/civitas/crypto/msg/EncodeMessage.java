package civitas.crypto.msg;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import civitas.crypto.CryptoException;
import civitas.crypto.parameters.ElGamalParameters;
import civitas.crypto.parameters.encoder.SchnorrPrimeEncode;
import civitas.util.CivitasBigInteger;
import civitas.util.CivitasBigIntegerFactory;

@Controller
public class EncodeMessage {
	protected static final String CHARSET = "UTF-8";

	@Autowired
	SchnorrPrimeEncode schnorrPrimeEncoder;

	public CivitasBigInteger apply(CivitasBigInteger plaintext,
			ElGamalParameters params) throws CryptoException {
        return schnorrPrimeEncoder.apply(plaintext, params);
	}

	public CivitasBigInteger apply(int i, ElGamalParameters params)
			throws CryptoException {
		return apply(CivitasBigIntegerFactory.obtain(i), params);
	}

	public CivitasBigInteger apply(String s, ElGamalParameters params)
			throws CryptoException {
		try {
			return apply(CivitasBigIntegerFactory.obtain(s.getBytes(CHARSET)),
					params);
		} catch (Exception e) {
			throw new CryptoException("I thought it is impossible");
		}
	}

}
