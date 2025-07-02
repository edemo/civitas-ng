package civitas.crypto.ciphertext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import civitas.crypto.CryptoBase;
import civitas.crypto.CryptoError;
import civitas.crypto.msg.CryptMessage;
import civitas.crypto.parameters.ElGamalParameters;
import civitas.crypto.publickey.ElGamalPublicKey;
import civitas.crypto.reencryptfactor.ElGamalReencryptFactor;
import civitas.util.CivitasBigInteger;

@Controller
public class ElGamalEncrypt {
	@Autowired
	CryptoBase cryptoBase;

	public ElGamalCiphertext apply(ElGamalPublicKey key, CryptMessage msg)
			throws CryptoError {
		ElGamalParameters ps = key.params;
		CivitasBigInteger m = msg.getM();
		CivitasBigInteger r = cryptoBase.generateRandomElement(ps.q);
		CivitasBigInteger a = ps.g.modPow(r, ps.p);
		CivitasBigInteger b = m.modMultiply(key.y.modPow(r, ps.p), ps.p);
		return new ElGamalCiphertext(a, b);
	}

	public ElGamalCiphertext apply(ElGamalPublicKey key, CryptMessage msg,
			ElGamalReencryptFactor encryptFactor) throws CryptoError {
		ElGamalParameters ps = key.params;
		CivitasBigInteger r = encryptFactor.r;
		CivitasBigInteger m = msg.getM();
		CivitasBigInteger a = ps.g.modPow(r, ps.p);
		CivitasBigInteger s = key.y.modPow(r, ps.p);
		CivitasBigInteger b = m.modMultiply(s, ps.p);
		return new ElGamalCiphertext(a, b);
	}

}
