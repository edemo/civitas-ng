package civitas.crypto.proofvote;

import java.util.LinkedList;
import java.util.List;

import civitas.crypto.ciphertext.ElGamalCiphertextC;
import civitas.crypto.messagedigest.MessageDigest;
import civitas.crypto.messagedigest.ObtainMessageDigest;
import civitas.crypto.parameters.ElGamalParametersC;
import civitas.util.CivitasBigInteger;
import civitas.util.Use;

public class CalculateProofEnvironment {
	@Use
	ObtainMessageDigest obtainMessageDigest;

	public List<CivitasBigInteger> apply(ElGamalParametersC params,
			ElGamalCiphertextC encCapability, ElGamalCiphertextC encChoice,
			String context) {
		List<CivitasBigInteger> E = new LinkedList<>();
		E.add(params.g);
		E.add(encCapability.a);
		E.add(encCapability.b);
		E.add(encChoice.a);
		E.add(encChoice.b);
		MessageDigest md = obtainMessageDigest.apply();
		md.update(context.getBytes());
		E.add(new CivitasBigInteger(1, md.digest()));

		return E;
	}

}
