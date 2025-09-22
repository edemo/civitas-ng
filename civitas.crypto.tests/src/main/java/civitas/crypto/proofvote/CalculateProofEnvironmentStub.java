package civitas.crypto.proofvote;

import java.io.UnsupportedEncodingException;

import civitas.crypto.messagedigest.CryptoHashStub;

public class CalculateProofEnvironmentStub {

	public static CalculateProofEnvironment stub() throws UnsupportedEncodingException {
		CalculateProofEnvironment mock = new CalculateProofEnvironment();
		mock.cryptoHash = CryptoHashStub.stub();
		return mock;
	}
}
