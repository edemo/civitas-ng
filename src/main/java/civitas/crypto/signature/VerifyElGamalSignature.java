package civitas.crypto.signature;

import civitas.crypto.CryptoError;
import civitas.crypto.algorithms.CryptoHash;
import civitas.crypto.parameters.ElGamalParameters;
import civitas.crypto.signedciphertext.ElGamalSignedCiphertext;
import civitas.crypto.signedciphertext.ElGamalSignedCiphertextC;
import civitas.util.CivitasBigInteger;
import civitas.util.Use;

public class VerifyElGamalSignature {
	@Use
	CryptoHash cryptoHash;

	public boolean apply(ElGamalParameters params,
			ElGamalSignedCiphertext ciphertext, byte[] additionalEnv)
			throws CryptoError {
		ElGamalParameters ps = params;
		ElGamalSignedCiphertextC cc = (ElGamalSignedCiphertextC) ciphertext;
		// to verify, check that c == h(g^d * a^(-c), a, b)
		CivitasBigInteger x = ps.g.modPow(cc.d.mod(ps.q), ps.p)
				.modMultiply(cc.a.modPow(cc.c.modNegate(ps.q), ps.p), ps.p);
		CivitasBigInteger v = cryptoHash.apply(x, cc.a, cc.b, additionalEnv)
				.mod(ps.q);
		return cc.c.equals(v);
	}

	public boolean apply(ElGamalParameters params,
			ElGamalSignedCiphertext ciphertext) throws CryptoError {
		return apply(params, ciphertext, null);

	}

}
