package civitas.crypto.reencryptfactor;

import civitas.crypto.CryptoError;
import civitas.crypto.algorithms.GenerateRandomElement;
import civitas.crypto.parameters.ElGamalParameters;
import civitas.util.Use;

public class GenerateElGamalReencryptFactor {

	@Use
	GenerateRandomElement generateRandomElement;

	public ElGamalReencryptFactor apply(ElGamalParameters params)
			throws CryptoError {
		ElGamalParameters ps = params;
		return new ElGamalReencryptFactor(generateRandomElement.apply(ps.q));
	}

}
