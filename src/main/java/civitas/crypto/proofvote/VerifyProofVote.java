package civitas.crypto.proofvote;

import java.util.List;

import civitas.crypto.algorithms.ConvertHashToBigInt;
import civitas.crypto.algorithms.GenerateRandomElement;
import civitas.crypto.ciphertext.ElGamalCiphertext;
import civitas.crypto.messagedigest.CryptoHash;
import civitas.crypto.parameters.ElGamalParameters;
import civitas.util.CivitasBigInteger;
import civitas.util.Use;

public class VerifyProofVote {

	@Use
	private static GenerateRandomElement generateRandomElement;
	@Use
	CryptoHash cryptoHash;
	@Use
	CalculateProofEnvironment calculateProofEnvironment;
	@Use
	ConvertHashToBigInt convertHashToBigInt;

	public boolean apply(ProofVote that, ElGamalParameters params,
			ElGamalCiphertext encCapability, ElGamalCiphertext encChoice,
			String context) {
		CivitasBigInteger a1 = encCapability.a;
		CivitasBigInteger a2 = encChoice.a;
		CivitasBigInteger p = params.p;
		CivitasBigInteger q = params.q;

		List<CivitasBigInteger> E = calculateProofEnvironment.apply(params,
				encCapability, encChoice, context);
		E.add(params.g.modPow(that.s1, p).modMultiply(a1.modPow(that.c, p), p));
		E.add(params.g.modPow(that.s2, p).modMultiply(a2.modPow(that.c, p), p));

		CivitasBigInteger x = convertHashToBigInt.apply(cryptoHash.apply(E)).mod(q);
		boolean ret = that.c.equals(x);
		return ret;
	}

}
