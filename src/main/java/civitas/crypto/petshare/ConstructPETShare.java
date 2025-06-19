package civitas.crypto.petshare;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import civitas.crypto.algorithms.GenerateRandomElement;
import civitas.crypto.ciphertext.ElGamalCiphertextish;
import civitas.crypto.parameters.ElGamalParameters;
import civitas.util.CivitasBigInteger;

@Service
public class ConstructPETShare {

	@Autowired
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
