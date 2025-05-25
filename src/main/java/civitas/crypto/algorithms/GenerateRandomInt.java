package civitas.crypto.algorithms;

import civitas.util.Use;

public class GenerateRandomInt {

	@Use
	GetRandomGenerator getRandomGenerator;

	public int apply(int n) {
		if (n <= 0)
			return 0;
		return getRandomGenerator.apply().nextInt(n);
	}

}
