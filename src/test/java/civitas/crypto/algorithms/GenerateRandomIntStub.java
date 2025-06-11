package civitas.crypto.algorithms;

import civitas.crypto.BasicValuesTestData;
import civitas.util.DI;

class GenerateRandomIntStub implements BasicValuesTestData {
	public static GenerateRandomInt stub() {
		return DI.get(GenerateRandomInt.class);
	}

}
