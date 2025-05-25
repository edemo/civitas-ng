package civitas.crypto.algorithms;

import civitas.crypto.ElGamalCiphertext;
import civitas.crypto.ElGamalParameters;
import civitas.crypto.PETShare;
import civitas.crypto.concrete.ElGamalCiphertextC;
import civitas.crypto.concrete.ElGamalParametersC;
import civitas.crypto.concrete.PETShareC;
import civitas.util.CivitasBigInteger;
import civitas.util.Use;

public class ConstructPETShare {

	@Use
	GenerateRandomElement generateRandomElement;

	public PETShare apply(ElGamalParameters prms, ElGamalCiphertext a,
			ElGamalCiphertext b) {
		if (a == null || !(a instanceof ElGamalCiphertextC))
			return null;
		if (b == null || !(b instanceof ElGamalCiphertextC))
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
