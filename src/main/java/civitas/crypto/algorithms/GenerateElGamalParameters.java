package civitas.crypto.algorithms;

import civitas.crypto.ElGamalParameters;
import civitas.crypto.SchnorrPrime;
import civitas.crypto.concrete.ElGamalParametersC;
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
