package civitas.crypto.msg;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import civitas.crypto.CryptoError;
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

	public ElGamalMsg apply(ElGamalPrivateKey key,
			ElGamalCiphertextish ciphertext, byte[] additionalEnv)
			throws CryptoException, CryptoError {
		ElGamalPrivateKey k = key;
		ElGamalParameters ps = key.params;

		if (ciphertext instanceof ElGamalSignedCiphertext) {
			if (!verifyElGamalSignature.apply(ps,
					(ElGamalSignedCiphertext) ciphertext, additionalEnv)) {
				throw new CryptoException("Ciphertext failed verification");
			}
		}
		ElGamalCiphertextish c = ciphertext;
		CivitasBigInteger a = c.getA();
		CivitasBigInteger b = c.getB();
		CivitasBigInteger m = b.modDivide(a.modPow(k.x, ps.p), ps.p);
		return new ElGamalMsg(m);
	}

	public ElGamalMsg apply(ElGamalPrivateKey key,
			ElGamalCiphertextish ciphertext) throws CryptoException, CryptoError {
		return apply(key, ciphertext, null);
	}

}
