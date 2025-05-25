package civitas.crypto.algorithms;

import civitas.crypto.CryptoError;
import civitas.crypto.ElGamalParameters;
import civitas.crypto.ElGamalSignedCiphertext;
import civitas.crypto.concrete.ElGamalParametersC;
import civitas.crypto.concrete.ElGamalSignedCiphertextC;
import civitas.util.CivitasBigInteger;
import civitas.util.Use;

public class VerifyElGamalSignature {
	@Use
	CryptoHash cryptoHash;

	public boolean apply(ElGamalParameters params,
			ElGamalSignedCiphertext ciphertext, byte[] additionalEnv)
			throws CryptoError {
		try {
			ElGamalParametersC ps = (ElGamalParametersC) params;
			ElGamalSignedCiphertextC cc = (ElGamalSignedCiphertextC) ciphertext;
			// to verify, check that c == h(g^d * a^(-c), a, b)
			CivitasBigInteger x = ps.g.modPow(cc.d.mod(ps.q), ps.p)
					.modMultiply(cc.a.modPow(cc.c.modNegate(ps.q), ps.p), ps.p);
			CivitasBigInteger v = cryptoHash.apply(x, cc.a, cc.b, additionalEnv)
					.mod(ps.q);
			return cc.c.equals(v);
		} catch (ClassCastException e) {
			throw new CryptoError(e);
		}
	}

}
