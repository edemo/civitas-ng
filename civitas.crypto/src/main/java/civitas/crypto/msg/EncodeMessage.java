package civitas.crypto.msg;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import civitas.crypto.CryptoException;
import civitas.crypto.parameters.ElGamalParameters;
import civitas.crypto.parameters.encoder.SchnorrPrimeEncode;
import civitas.util.CivitasBigInteger;
import civitas.util.CivitasBigIntegerFactory;

@Controller
public class EncodeMessage {
	protected static final Charset CHARSET = StandardCharsets.UTF_8;

	@Autowired
	SchnorrPrimeEncode schnorrPrimeEncoder;

	public CivitasBigInteger apply(final CivitasBigInteger plaintext, final ElGamalParameters params)
			throws CryptoException {
		return schnorrPrimeEncoder.apply(plaintext, params);
	}

	public CivitasBigInteger apply(final int i, final ElGamalParameters params) throws CryptoException {
		return apply(CivitasBigIntegerFactory.obtain(i), params);
	}

	public CivitasBigInteger apply(final String s, final ElGamalParameters params) throws CryptoException {
		try {
			return apply(CivitasBigIntegerFactory.obtain(s.getBytes(CHARSET)), params);
		} catch (Exception e) {
			throw new CryptoException("I thought it is impossible");
		}
	}
}
