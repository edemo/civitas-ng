package civitas.crypto.reencryptfactor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import civitas.crypto.CryptoError;
import civitas.crypto.algorithms.GenerateRandomElement;
import civitas.crypto.parameters.ElGamalParameters;

@Service
public class GenerateElGamalReencryptFactor {

	@Autowired
	GenerateRandomElement generateRandomElement;

	public ElGamalReencryptFactor apply(ElGamalParameters params)
			throws CryptoError {
		ElGamalParameters ps = params;
		return new ElGamalReencryptFactor(generateRandomElement.apply(ps.q));
	}

}
