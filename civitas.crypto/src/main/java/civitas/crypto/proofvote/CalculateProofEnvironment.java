package civitas.crypto.proofvote;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import civitas.crypto.ciphertext.ElGamalCiphertextish;
import civitas.crypto.messagedigest.CryptoHash;
import civitas.crypto.parameters.ElGamalParameters;
import civitas.util.CivitasBigInteger;
import civitas.util.CivitasBigIntegerFactory;

@Controller
public class CalculateProofEnvironment {
	@Autowired
	CryptoHash cryptoHash;

	public List<CivitasBigInteger> apply(ElGamalParameters params,
			ElGamalCiphertextish encCapability, ElGamalCiphertextish encChoice,
			String context) {
		List<CivitasBigInteger> e = new LinkedList<>();
		e.add(params.g);
		e.add(encCapability.getA());
		e.add(encCapability.getB());
		e.add(encChoice.getA());
		e.add(encChoice.getB());
		e.add(CivitasBigIntegerFactory.obtain(1,
				cryptoHash.apply(context.getBytes())));
		return e;
	}

}
