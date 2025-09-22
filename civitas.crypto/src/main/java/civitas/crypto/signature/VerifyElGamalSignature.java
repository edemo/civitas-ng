package civitas.crypto.signature;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import civitas.crypto.messagedigest.CryptoHash;
import civitas.crypto.parameters.ElGamalParameters;
import civitas.crypto.signedciphertext.ElGamalSignedCiphertext;
import civitas.util.CivitasBigInteger;

@Controller
public class VerifyElGamalSignature {
	@Autowired
	CryptoHash cryptoHash;

	public boolean apply(ElGamalParameters params,
			ElGamalSignedCiphertext ciphertext, byte[] additionalEnv) {
		CivitasBigInteger x = params.g.modPow(ciphertext.d.mod(params.q), params.p)
				.modMultiply(
						ciphertext.a.modPow(ciphertext.c.modNegate(params.q), params.p),
                        params.p);
		CivitasBigInteger v = cryptoHash
				.apply(x, ciphertext.a, ciphertext.b, additionalEnv).mod(params.q);
		return ciphertext.c.equals(v);
	}

	public boolean apply(ElGamalParameters params,
			ElGamalSignedCiphertext ciphertext) {
		return apply(params, ciphertext, null);

	}

}
