package civitas.crypto.proofvote;

import java.util.LinkedList;
import java.util.List;

import civitas.crypto.ciphertext.ElGamalCiphertext;
import civitas.crypto.messagedigest.CryptoHash;
import civitas.crypto.parameters.ElGamalParameters;
import civitas.util.CivitasBigInteger;
import civitas.util.Use;

public class CalculateProofEnvironment {
	@Use
	CryptoHash cryptoHash;

	public List<CivitasBigInteger> apply(ElGamalParameters params,
			ElGamalCiphertext encCapability, ElGamalCiphertext encChoice,
			String context) {
		List<CivitasBigInteger> E = new LinkedList<>();
		E.add(params.g);
		E.add(encCapability.a);
		E.add(encCapability.b);
		E.add(encChoice.a);
		E.add(encChoice.b);
		E.add(new CivitasBigInteger(1, cryptoHash.apply(context.getBytes())));
		return E;
	}

}
