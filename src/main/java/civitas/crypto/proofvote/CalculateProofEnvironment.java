package civitas.crypto.proofvote;

import java.util.LinkedList;
import java.util.List;

import civitas.crypto.ciphertext.ElGamalCiphertextish;
import civitas.crypto.messagedigest.CryptoHash;
import civitas.crypto.parameters.ElGamalParameters;
import civitas.util.CivitasBigInteger;
import civitas.util.Use;

public class CalculateProofEnvironment {
	@Use
	CryptoHash cryptoHash;

	public List<CivitasBigInteger> apply(ElGamalParameters params,
			ElGamalCiphertextish encCapability, ElGamalCiphertextish encChoice,
			String context) {
		List<CivitasBigInteger> E = new LinkedList<>();
		E.add(params.g);
		E.add(encCapability.getA());
		E.add(encCapability.getB());
		E.add(encChoice.getA());
		E.add(encChoice.getB());
		E.add(new CivitasBigInteger(1, cryptoHash.apply(context.getBytes())));
		return E;
	}

}
