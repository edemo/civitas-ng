package civitas.crypto.parameters;

import civitas.crypto.SchnorrPrime;
import civitas.crypto.algorithms.Constants;
import civitas.crypto.algorithms.FindGenerator;
import civitas.crypto.algorithms.GenerateSafePrime;
import civitas.crypto.algorithms.GenerateSchnorrPrime;
import civitas.util.CivitasBigInteger;
import civitas.util.Use;

public class GenerateElGamalParameters implements Constants {

	@Use
	GenerateSafePrime generateSafePrime;
	@Use
	GenerateSchnorrPrime generateSchnorrPrime;
	@Use
	FindGenerator findGenerator;

	public ElGamalParametersC apply(int keyLength, int groupLength) {
		SchnorrPrime sp;
		if (groupLength == keyLength + 1) {
			sp = generateSafePrime.apply(keyLength);
		} else {
			sp = generateSchnorrPrime.apply(keyLength, groupLength);
		}
		CivitasBigInteger g = findGenerator.apply(sp);
		return new ElGamalParametersC(sp.p, sp.q, g);
	}

	public ElGamalParameters apply() {
		return apply(EL_GAMAL_KEY_LENGTH, EL_GAMAL_GROUP_LENGTH);
	}

}
