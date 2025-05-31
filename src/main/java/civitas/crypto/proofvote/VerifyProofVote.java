package civitas.crypto.proofvote;

import java.util.List;

import civitas.crypto.algorithms.ConvertHashToBigInt;
import civitas.crypto.algorithms.CryptoHash;
import civitas.crypto.algorithms.GenerateRandomElement;
import civitas.crypto.ciphertext.ElGamalCiphertext;
import civitas.crypto.parameters.ElGamalParameters;
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

	public boolean apply(ProofVoteC that, ElGamalParameters params,
			ElGamalCiphertext encCapability, ElGamalCiphertext encChoice,
			String context) {
		try {
			ElGamalParameters paramsC = (ElGamalParameters) params;
			ElGamalCiphertext encCapabilityC = (ElGamalCiphertext) encCapability;
			ElGamalCiphertext encChoiceC = (ElGamalCiphertext) encChoice;
			CivitasBigInteger a1 = encCapabilityC.a;
			CivitasBigInteger a2 = encChoiceC.a;
			CivitasBigInteger p = paramsC.p;
			CivitasBigInteger q = paramsC.q;

			List<CivitasBigInteger> E = calculateProofEnvironment.apply(paramsC,
					encCapabilityC, encChoiceC, context);
			E.add(paramsC.g.modPow(that.s1, p).modMultiply(a1.modPow(that.c, p), p));
			E.add(paramsC.g.modPow(that.s2, p).modMultiply(a2.modPow(that.c, p), p));
			CivitasBigInteger x = convertHashToBigInt.apply(cryptoHash.apply(E))
					.mod(q);
			boolean ret = that.c.equals(x);
			return ret;
		} catch (ClassCastException e) {
			e.printStackTrace();
			return false;
		}
	}

}
