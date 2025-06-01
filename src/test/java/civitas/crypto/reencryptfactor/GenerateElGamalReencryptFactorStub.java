package civitas.crypto.reencryptfactor;

import civitas.util.DI;

public class GenerateElGamalReencryptFactorStub {

	public static GenerateElGamalReencryptFactor stub() {
		GenerateElGamalReencryptFactor mock = DI
				.get(GenerateElGamalReencryptFactor.class);
		return mock;
	}
}
