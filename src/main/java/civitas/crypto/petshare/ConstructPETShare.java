package civitas.crypto.petshare;

import civitas.crypto.algorithms.GenerateRandomElement;
import civitas.crypto.ciphertext.ElGamalCiphertext;
import civitas.crypto.ciphertext.ElGamalCiphertextC;
import civitas.crypto.parameters.ElGamalParameters;
import civitas.crypto.parameters.ElGamalParametersC;
import civitas.util.CivitasBigInteger;
import civitas.util.Use;

public class ConstructPETShare {

	@Use
	GenerateRandomElement generateRandomElement;

	public PETShare apply(ElGamalParameters prms, ElGamalCiphertext a,
			ElGamalCiphertext b) {
		if (a == null || !(a instanceof ElGamalCiphertextC) || b == null || !(b instanceof ElGamalCiphertextC))
			return null;
		if (prms == null || !(prms instanceof ElGamalParametersC))
			return null;
		ElGamalParametersC params = (ElGamalParametersC) prms;
		ElGamalCiphertextC ac = (ElGamalCiphertextC) a;
		ElGamalCiphertextC bc = (ElGamalCiphertextC) b;

		CivitasBigInteger z = generateRandomElement.apply(params.q);
		return new PETShareC(ac, bc, z);
	}

}
