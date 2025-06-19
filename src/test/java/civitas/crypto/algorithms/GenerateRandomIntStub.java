package civitas.crypto.algorithms;

import civitas.DI;
import civitas.crypto.BasicValuesTestData;

class GenerateRandomIntStub implements BasicValuesTestData {
	public static GenerateRandomInt stub() {
		return DI.get(GenerateRandomInt.class);
	}

}
