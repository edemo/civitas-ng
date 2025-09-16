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
		CivitasBigInteger p = params.p();
		CivitasBigInteger q = params.q();

		List<CivitasBigInteger> e = calculateProofEnvironment.apply(params,
				encCapability, encChoice, context);
		e.add(params.g().modPow(that.s1(), p).modMultiply(a1.modPow(that.c(), p), p));
		e.add(params.g().modPow(that.s2(), p).modMultiply(a2.modPow(that.c(), p), p));

		byte[] hash = cryptoHash.apply(e);
		CivitasBigInteger x = convertHashToBigInt.apply(hash).mod(q);
        return that.c().equals(x);
	}

}
