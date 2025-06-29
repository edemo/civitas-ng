package civitas.crypto.proofvote;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import civitas.crypto.algorithms.ConvertHashToBigInt;
import civitas.crypto.ciphertext.ElGamalCiphertextish;
import civitas.crypto.messagedigest.CryptoHash;
import civitas.crypto.parameters.ElGamalParameters;
import civitas.util.CivitasBigInteger;

@Controller
public class VerifyProofVote {

	@Autowired
	CryptoHash cryptoHash;
	@Autowired
	CalculateProofEnvironment calculateProofEnvironment;
	@Autowired
	ConvertHashToBigInt convertHashToBigInt;

	public boolean apply(ProofVote that, ElGamalParameters params,
			ElGamalCiphertextish encCapability, ElGamalCiphertextish encChoice,
			String context) {
		CivitasBigInteger a1 = encCapability.getA();
		CivitasBigInteger a2 = encChoice.getA();
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
