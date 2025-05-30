package civitas.crypto.petshare;

import civitas.crypto.algorithms.GenerateRandomElement;
import civitas.crypto.ciphertext.ElGamalCiphertext;
import civitas.crypto.parameters.ElGamalParameters;
import civitas.crypto.parameters.ElGamalParametersC;
import civitas.util.CivitasBigInteger;
import civitas.util.Use;

public class ConstructPETShare {

	@Use
	GenerateRandomElement generateRandomElement;

	public PETShare apply(ElGamalParameters prms, ElGamalCiphertext a,
			ElGamalCiphertext b) {
		if (a == null || b == null)
			return null;
		if (prms == null || !(prms instanceof ElGamalParametersC))
			return null;
		ElGamalParametersC params = (ElGamalParametersC) prms;
		ElGamalCiphertext ac = a;
		ElGamalCiphertext bc = b;

		CivitasBigInteger z = generateRandomElement.apply(params.q);
		return new PETShareC(ac, bc, z);
	}

}
