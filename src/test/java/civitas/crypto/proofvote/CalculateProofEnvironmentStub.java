package civitas.crypto.proofvote;

import civitas.util.DI;

public class CalculateProofEnvironmentStub {

	public static CalculateProofEnvironment stub() {
		CalculateProofEnvironment calculateProofEnvironment = new CalculateProofEnvironment();
		DI.fill(calculateProofEnvironment);
		return calculateProofEnvironment;
	}
}
