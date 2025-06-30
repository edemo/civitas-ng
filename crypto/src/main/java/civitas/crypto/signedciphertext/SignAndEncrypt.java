package civitas.crypto.signedciphertext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import civitas.crypto.CryptoError;
import civitas.crypto.algorithms.GenerateRandomElement;
import civitas.crypto.messagedigest.CryptoHash;
import civitas.crypto.msg.CryptMessage;
import civitas.crypto.parameters.ElGamalParameters;
import civitas.crypto.publickey.ElGamalPublicKey;
import civitas.crypto.reencryptfactor.ElGamalReencryptFactor;
import civitas.crypto.reencryptfactor.GenerateElGamalReencryptFactor;
import civitas.util.CivitasBigInteger;

@Controller
public class SignAndEncrypt {

	@Autowired
	GenerateRandomElement generateRandomElement;
	@Autowired
	CryptoHash cryptoHash;
	@Autowired
	GenerateElGamalReencryptFactor generateElGamalReencryptFactor;

	public ElGamalSignedCiphertext apply(ElGamalPublicKey key, CryptMessage msg,
			ElGamalReencryptFactor r, byte[] additionalEnv) throws CryptoError {
		ElGamalParameters ps = key.params;
		CivitasBigInteger m = msg.getM();
		CivitasBigInteger rr = r.r;
		CivitasBigInteger s = generateRandomElement.apply(ps.q);
		CivitasBigInteger a = ps.g.modPow(rr, ps.p);
		CivitasBigInteger b = m.modMultiply(key.y.modPow(rr, ps.p), ps.p);
		CivitasBigInteger c = cryptoHash
				.apply(ps.g.modPow(s, ps.p), a, b, additionalEnv).mod(ps.q);
		CivitasBigInteger d = s.modAdd(c.modMultiply(rr, ps.q), ps.q);
		return new ElGamalSignedCiphertext(a, b, c, d);
	}

}
