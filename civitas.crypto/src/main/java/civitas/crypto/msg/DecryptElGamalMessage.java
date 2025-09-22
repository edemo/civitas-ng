package civitas.crypto.msg;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import civitas.crypto.CryptoException;
import civitas.crypto.ciphertext.ElGamalCiphertextish;
import civitas.crypto.parameters.ElGamalParameters;
import civitas.crypto.privatekey.ElGamalPrivateKey;
import civitas.crypto.signature.VerifyElGamalSignature;
import civitas.crypto.signedciphertext.ElGamalSignedCiphertext;
import civitas.util.CivitasBigInteger;

@Controller
public class DecryptElGamalMessage {

	@Autowired
	VerifyElGamalSignature verifyElGamalSignature;

	public ElGamalMsg apply(final ElGamalPrivateKey key,
			final ElGamalCiphertextish ciphertext, final byte[] additionalEnv)
			throws CryptoException {
		ElGamalPrivateKey k = key;
		ElGamalParameters ps = key.params();

		if (ciphertext instanceof ElGamalSignedCiphertext && !verifyElGamalSignature
				.apply(ps, (ElGamalSignedCiphertext) ciphertext, additionalEnv)) {
			throw new CryptoException("Ciphertext failed verification");
		}

		ElGamalCiphertextish c = ciphertext;
		CivitasBigInteger a = c.getA();
		CivitasBigInteger b = c.getB();
		CivitasBigInteger m = b.modDivide(a.modPow(k.x(), ps.p), ps.p);
		return new ElGamalMsg(m);
	}

	public ElGamalMsg apply(final ElGamalPrivateKey key,
			final ElGamalCiphertextish ciphertext) throws CryptoException {
		return apply(key, ciphertext, null);
	}

}
