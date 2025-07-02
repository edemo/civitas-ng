package civitas.crypto.proofvote;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import civitas.crypto.CryptoBase;
import civitas.crypto.ciphertext.ElGamalCiphertextish;
import civitas.crypto.messagedigest.CryptoHash;
import civitas.crypto.parameters.ElGamalParameters;
import civitas.crypto.reencryptfactor.ElGamalReencryptFactor;
import civitas.util.CivitasBigInteger;

@Controller
public class ConstructProofVote {
	@Autowired
	CryptoBase cryptoBase;
	@Autowired
	CryptoHash cryptoHash;
	@Autowired
	CalculateProofEnvironment calculateProofEnvironment;

	public ProofVote apply(ElGamalParameters params,
			ElGamalCiphertextish encCapability, ElGamalCiphertextish encChoice,
			String context, ElGamalReencryptFactor alpha1,
			ElGamalReencryptFactor alpha2) {

		CivitasBigInteger r1 = cryptoBase.generateRandomElement(params.q);
		CivitasBigInteger r2 = cryptoBase.generateRandomElement(params.q);
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
		return new ProofVote(c, s1, s2);
	}

}
