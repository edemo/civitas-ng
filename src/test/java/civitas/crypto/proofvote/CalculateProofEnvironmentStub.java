package civitas.crypto.proofvote;

import civitas.crypto.messagedigest.CryptoHashStub;

public class CalculateProofEnvironmentStub {

	public static CalculateProofEnvironment stub() {
		CalculateProofEnvironment mock = new CalculateProofEnvironment();
		mock.cryptoHash = CryptoHashStub.stub();
		return mock;
	}
}
