package civitas.crypto.algorithms;

import java.util.LinkedList;
import java.util.List;

import civitas.crypto.MessageDigest;
import civitas.crypto.concrete.ElGamalCiphertextC;
import civitas.crypto.concrete.ElGamalParametersC;
import civitas.external.ObtainMessageDigest;
import civitas.util.CivitasBigInteger;
import civitas.util.Use;

public class CalculateProofEnvironment {
	@Use
	ObtainMessageDigest obtainMessageDigest;

	List<CivitasBigInteger> apply(ElGamalParametersC params,
			ElGamalCiphertextC encCapability, ElGamalCiphertextC encChoice,
			String context) {
		List<CivitasBigInteger> E = new LinkedList<CivitasBigInteger>();
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
