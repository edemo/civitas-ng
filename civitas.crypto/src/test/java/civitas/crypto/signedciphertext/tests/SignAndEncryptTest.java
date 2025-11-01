package civitas.crypto.signedciphertext.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import civitas.common.ballotdesign.tests.BallotDesignTestData;
import civitas.common.tests.RandomAwareTestBase;
import civitas.crypto.signedciphertext.SignAndEncrypt;

class SignAndEncryptTest extends RandomAwareTestBase implements ElGamalSignedCiphertextTestData, BallotDesignTestData {

	@InjectMocks
	SignAndEncrypt signAndEncrypt;

	@Test
	@DisplayName("elGamalSignedEncrypt works as expected:" + "s:= random, "
			+ "a:=g^y (mod p), " + "b:=m*key^y (mod p), "
			+ "c:=hash(g^s,a,b,env) % q, " + "d:=s+c*y (mod q)")
	void elGamalSignedEncryptTest() {

		assertEquals(
				EL_GAMAL_SIGNED_CIPHERTEXT,
				signAndEncrypt.apply(
						EL_GAMAL_PUBLIC_KEY_E,
						EL_GAMAL_MESSAGE_VOTE_CAPABILITY_SHARE,
						ELGAMAL_REENCRYPT_FACTOR_E,
						ADDITIONALENV_BYTES));
	}
}
