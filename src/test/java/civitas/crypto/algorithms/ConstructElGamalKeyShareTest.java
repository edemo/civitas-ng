package civitas.crypto.algorithms;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import civitas.crypto.CryptoError;
import civitas.crypto.ElGamalKeyShare;
import civitas.crypto.ElGamalProofKnowDiscLog;
import civitas.crypto.ElGamalPublicKey;
import civitas.crypto.concrete.ConcreteTestBase;
import civitas.crypto.concrete.ElGamalKeyPairShareTestData;
import civitas.crypto.concrete.ElGamalKeyShareTestData;
import civitas.util.Tested;

public class ConstructElGamalKeyShareTest extends ConcreteTestBase
		implements ElGamalKeyPairShareTestData, ElGamalKeyShareTestData {

	@Tested
	ConstructElGamalKeyShare constructElGamalKeyShare;

	@Test
	@DisplayName("the one-parameter version returns the public key with the proof of knowledge for the private key")
	void test() {
		ElGamalKeyShare actual = constructElGamalKeyShare
				.apply(EL_GAMAL_KEYPAIR_SHARE_NO_PROOF);
		assertTrue(EL_GAMAL_KEY_SHARE_E.equals(actual));
	}

	@Test
	@DisplayName("the one-parameter version tests the created proof")
	void test_1() {
		constructElGamalKeyShare.apply(EL_GAMAL_KEYPAIR_SHARE_NO_PROOF);
		verify(constructElGamalKeyShare.verifyElGamalKeyShare)
				.apply(EL_GAMAL_KEY_SHARE_E);
	}

	@Test
	@DisplayName("if the generated proof does not verify (impossible) a CryptoError is thrown")
	void test_2() {
		assertThrows(CryptoError.class, () -> constructElGamalKeyShare
				.apply(EL_GAMAL_KEYPAIR_SHARE_BAD_PROOF_GENERATED));
	}

	@Test
	@DisplayName("throws CryptoError if the pubkey is not of type ElGamalPublicKeyC")
	void test2() {
		assertThrows(CryptoError.class, () -> constructElGamalKeyShare
				.apply(mock(ElGamalPublicKey.class), EL_GAMAL_PROOF_KNOWN_DISC_LOG));
	}

	@Test
	@DisplayName("throws CryptoError if the proof is not of type ElGamalProofKnowDiscLogC")
	void test3() {
		assertThrows(CryptoError.class, () -> constructElGamalKeyShare
				.apply(EL_GAMAL_PUBLIC_KEY_E, mock(ElGamalProofKnowDiscLog.class)));
	}

}
