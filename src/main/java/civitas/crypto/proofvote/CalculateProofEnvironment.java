package civitas.crypto.proofvote;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import civitas.crypto.ciphertext.ElGamalCiphertextish;
import civitas.crypto.messagedigest.CryptoHash;
import civitas.crypto.parameters.ElGamalParameters;
import civitas.util.CivitasBigInteger;

@Service
public class CalculateProofEnvironment {
	@Autowired
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
