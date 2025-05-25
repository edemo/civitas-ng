package civitas.crypto.algorithms;

import java.util.List;

import civitas.crypto.ElGamalCiphertext;
import civitas.crypto.ElGamalParameters;
import civitas.crypto.concrete.CryptoFactoryC;
import civitas.crypto.concrete.ElGamalCiphertextC;
import civitas.crypto.concrete.ElGamalParametersC;
import civitas.crypto.concrete.ProofVoteC;
import civitas.util.CivitasBigInteger;
import civitas.util.Use;

public class VerifyProofVote {

	@Use
	private static GenerateRandomElement generateRandomElement;
	@Use
	CryptoHash cryptoHash;
	@Use
	CalculateProofEnvironment calculateProofEnvironment;

	public boolean apply(ProofVoteC that, ElGamalParameters params,
			ElGamalCiphertext encCapability, ElGamalCiphertext encChoice,
			String context) {
		try {
			CryptoFactoryC factory = CryptoFactoryC.singleton();
			ElGamalParametersC paramsC = (ElGamalParametersC) params;
			ElGamalCiphertextC encCapabilityC = (ElGamalCiphertextC) encCapability;
			ElGamalCiphertextC encChoiceC = (ElGamalCiphertextC) encChoice;
			CivitasBigInteger a1 = encCapabilityC.a;
			CivitasBigInteger a2 = encChoiceC.a;
			CivitasBigInteger p = paramsC.p;
			CivitasBigInteger q = paramsC.q;

			List<CivitasBigInteger> E = calculateProofEnvironment.apply(paramsC,
					encCapabilityC, encChoiceC, context);
			E.add(paramsC.g.modPow(that.s1, p).modMultiply(a1.modPow(that.c, p), p));
			E.add(paramsC.g.modPow(that.s2, p).modMultiply(a2.modPow(that.c, p), p));
			CivitasBigInteger x = factory.hashToBigInt(cryptoHash.apply(E)).mod(q);
			boolean ret = that.c.equals(x);
			return ret;
		} catch (ClassCastException e) {
			e.printStackTrace();
			return false;
		}
	}

}
