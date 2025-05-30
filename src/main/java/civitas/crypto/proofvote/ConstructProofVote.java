package civitas.crypto.proofvote;

import java.util.List;

import civitas.crypto.algorithms.CryptoHash;
import civitas.crypto.algorithms.GenerateRandomElement;
import civitas.crypto.ciphertext.ElGamalCiphertextC;
import civitas.crypto.parameters.ElGamalParametersC;
import civitas.crypto.reencryptfactor.ElGamalReencryptFactorC;
import civitas.util.CivitasBigInteger;
import civitas.util.Use;

public class ConstructProofVote {
	@Use
	GenerateRandomElement generateRandomElement;
	@Use
	CryptoHash cryptoHash;
	@Use
	CalculateProofEnvironment calculateProofEnvironment;

	public ProofVoteC apply(ElGamalParametersC params,
			ElGamalCiphertextC encCapability, ElGamalCiphertextC encChoice,
			String context, ElGamalReencryptFactorC alpha1,
			ElGamalReencryptFactorC alpha2) {

		CivitasBigInteger r1 = generateRandomElement.apply(params.q);
		CivitasBigInteger r2 = generateRandomElement.apply(params.q);
		List<CivitasBigInteger> E = calculateProofEnvironment.apply(params,
				encCapability, encChoice, context);
		E.add(params.g.modPow(r1, params.p));
		E.add(params.g.modPow(r2, params.p));

		CivitasBigInteger c = new CivitasBigInteger(1, cryptoHash.apply(E))
				.mod(params.q);
		CivitasBigInteger s1 = r1.modSubtract(c.modMultiply(alpha1.r, params.q),
				params.q);
		CivitasBigInteger s2 = r2.modSubtract(c.modMultiply(alpha2.r, params.q),
				params.q);
		return new ProofVoteC(c, s1, s2);
	}

}
