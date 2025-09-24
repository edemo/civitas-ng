package civitas.crypto.reencryptfactor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import civitas.crypto.CryptoBase;
import civitas.crypto.parameters.ElGamalParameters;

@Controller
public class GenerateElGamalReencryptFactor {

	@Autowired
	CryptoBase cryptoBase;

	public ElGamalReencryptFactor apply(final ElGamalParameters params) {
		return new ElGamalReencryptFactor(cryptoBase.generateRandomElement(params.q));
	}
}
