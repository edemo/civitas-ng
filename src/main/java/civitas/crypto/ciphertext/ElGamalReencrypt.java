package civitas.crypto.ciphertext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import civitas.crypto.CryptoError;
import civitas.crypto.algorithms.GenerateRandomElement;
import civitas.crypto.parameters.ElGamalParameters;
import civitas.crypto.publickey.ElGamalPublicKey;
import civitas.crypto.reencryptfactor.ElGamalReencryptFactor;
import civitas.util.CivitasBigInteger;

@Service
public class ElGamalReencrypt {

	@Autowired
	GenerateRandomElement generateRandomElement;

	public ElGamalCiphertextish apply(ElGamalPublicKey key,
			ElGamalCiphertextish ciphertext) throws CryptoError {
		ElGamalParameters ps = key.params;
		CivitasBigInteger c1 = ciphertext.getA();
		CivitasBigInteger c2 = ciphertext.getB();
		CivitasBigInteger y = generateRandomElement.apply(ps.q);
		c1 = c1.modMultiply(ps.g.modPow(y, ps.p), ps.p);
		c2 = c2.modMultiply(key.y.modPow(y, ps.p), ps.p);
		return new ElGamalCiphertext(c1, c2);
	}

	public ElGamalCiphertextish apply(ElGamalPublicKey key,
			ElGamalCiphertextish ciphertext, ElGamalReencryptFactor factor)
			throws CryptoError {
		ElGamalParameters ps = key.params;
		CivitasBigInteger a = ciphertext.getA();
		CivitasBigInteger b = ciphertext.getB();
		CivitasBigInteger r = factor.r;
		a = a.modMultiply(ps.g.modPow(r, ps.p), ps.p);
		b = b.modMultiply(key.y.modPow(r, ps.p), ps.p);
		return new ElGamalCiphertext(a, b);

	}

}
