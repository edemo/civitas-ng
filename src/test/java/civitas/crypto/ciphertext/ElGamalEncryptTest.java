package civitas.crypto.ciphertext;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Base64;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import civitas.common.TestBase;
import civitas.crypto.messagedigest.CryptoHash;
import civitas.crypto.messagedigest.CryptoHashStub;
import civitas.crypto.msg.ElGamalMsg;
import civitas.crypto.reencryptfactor.ElGamalReencryptFactor;
import civitas.crypto.reencryptfactor.ElGamalReencryptFactorTestData;

public class ElGamalEncryptTest extends TestBase
		implements ElGamalCiphertextTestData, ElGamalReencryptFactorTestData {

	@InjectMocks
	ElGamalEncrypt elGamalEncrypt;

	@Test
	@DisplayName("elGamalEncrypt with a random factor works as expected")
	void test0_1() throws Exception {

		ElGamalMsg msg = new ElGamalMsg(BIGINT_G.modPow(BIGINT_B, BIGINT_P));
		ElGamalCiphertext ENCRYPTED = new ElGamalCiphertext(
				BIGINT_G.modPow(RANDOMS_0, BIGINT_P),
				BIGINT_G.modPow(BIGINT_B, BIGINT_P).modMultiply(
						EL_GAMAL_PUBLIC_KEY_EPRIME.y.modPow(RANDOMS_0, BIGINT_P),
						BIGINT_P));

		assertEquals(ENCRYPTED,
				elGamalEncrypt.apply(EL_GAMAL_PUBLIC_KEY_EPRIME, msg));
	}

	@Test
	@DisplayName("elGamalEncrypt with a factor works as expected:"
			+ "c1:=g^factor ; s:=pubkey^factor ; c2:=m * s  (mod p)")
	void test0() throws Exception {
		assertEquals(ENCRYPTED_WITH_FACTOR_EPRIME,
				elGamalEncrypt.apply(EL_GAMAL_PUBLIC_KEY_EPRIME,
						EL_GAMAL_MESSAGE_VOTE_CAPABILITY_SHARE,
						new ElGamalReencryptFactor(FACTOR_EPRIME)));
	}

	@Test
	@DisplayName("elGamalEncrypt with factor zero results in c1=1, c2=msg"
			+ "FIXME: the pubkey is not used here. constructWellKnownCiphertexts uses this."
			+ "note from there:"
			+ "		// Note: the well known ciphertexts MUST be the encryptions of 1,2,3,...\n"
			+ "		// using the encryption factor 0. This is assumed by some of the\n"
			+ "		// zero knowledge proofs.")
	void test() throws Exception {
		ElGamalMsg msg = new ElGamalMsg(BIGINT_G.modPow(BIGINT_B, BIGINT_P));
		assertEquals(ENCRYPTED_ZERO_FACTOR, (elGamalEncrypt
				.apply(EL_GAMAL_PUBLIC_KEY_EPRIME, msg, ENCRYPT_FACTOR_ZERO)));
	}

	@Test
	@Tag("testdata")
	@DisplayName("VOTER_ADDITIONAL_ENV")
	void test1() {
		CryptoHash cryptoHash = CryptoHashStub.stub();
		assertEquals(Base64.getEncoder().encodeToString(VOTER_ADDITIONAL_ENV),
				Base64.getEncoder().encodeToString(cryptoHash.apply(8 + "bob")));
	}

}
