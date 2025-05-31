package civitas.crypto.parameters;

import civitas.crypto.Constants;
import civitas.util.CivitasBigInteger;
import civitas.util.Use;

public class GenerateElGamalParameters implements Constants {

	@Use
	GenerateSafePrime generateSafePrime;
	@Use
	GenerateSchnorrPrime generateSchnorrPrime;
	@Use
	FindGenerator findGenerator;

	public ElGamalParameters apply(int keyLength, int groupLength) {
		PrimePair sp;
		if (groupLength == keyLength + 1) {
			sp = generateSafePrime.apply(keyLength);
		} else {
			sp = generateSchnorrPrime.apply(keyLength, groupLength);
		}
		CivitasBigInteger g = findGenerator.apply(sp);
		return new ElGamalParameters(sp.p, sp.q, g);
	}

	public ElGamalParameters apply() {
		return apply(EL_GAMAL_KEY_LENGTH, EL_GAMAL_GROUP_LENGTH);
	}

}
