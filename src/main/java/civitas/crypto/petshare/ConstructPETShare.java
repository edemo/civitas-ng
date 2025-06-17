package civitas.crypto.petshare;

import civitas.crypto.algorithms.GenerateRandomElement;
import civitas.crypto.ciphertext.ElGamalCiphertextish;
import civitas.crypto.parameters.ElGamalParameters;
import civitas.util.CivitasBigInteger;
import civitas.util.Use;

public class ConstructPETShare {

	@Use
	GenerateRandomElement generateRandomElement;

	public PETShare apply(ElGamalParameters prms, ElGamalCiphertextish a,
			ElGamalCiphertextish b) {
		if (a == null || b == null || prms == null)
			return null;
		ElGamalParameters params = prms;
		CivitasBigInteger z = generateRandomElement.apply(params.q);
		return new PETShare(a, b, z);
	}

}
