package civitas.util;

import java.util.Random;

public class ObtainProbablePrime {

	public CivitasBigInteger apply(int bitLenght, int certainty, Random random) {
		return new CivitasBigInteger(bitLenght, certainty, random);
	}

}
