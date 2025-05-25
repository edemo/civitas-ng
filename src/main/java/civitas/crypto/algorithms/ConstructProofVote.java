package civitas.crypto.algorithms;

import java.util.List;

import civitas.common.Util;
import civitas.crypto.concrete.ElGamalCiphertextC;
import civitas.crypto.concrete.ElGamalParametersC;
import civitas.crypto.concrete.ElGamalReencryptFactorC;
import civitas.crypto.concrete.ProofVoteC;
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
		System.out.println(Util.fromBigInt(alpha1.r));
		CivitasBigInteger s2 = r2.modSubtract(c.modMultiply(alpha2.r, params.q),
				params.q);
		return new ProofVoteC(c, s1, s2);
	}

}
