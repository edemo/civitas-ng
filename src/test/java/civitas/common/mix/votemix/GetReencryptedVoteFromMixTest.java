package civitas.common.mix.votemix;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import civitas.common.TestBase;
import civitas.common.VoteTestData;
import civitas.util.Tested;

class GetReencryptedVoteFromMixTest extends TestBase
		implements VoteTestData, VoteMixTestData {

	@Tested
	GetReencryptedVoteFromMix getReencryptedVoteFromMix;

	@Test
	@DisplayName("getsh the reencryption of ith vote from the mix")
	void test() {
		assertEquals(TO_VOTE,
				getReencryptedVoteFromMix.apply(FROM_MIX, 0, ELGAMAL_REENCRYPT_FACTOR_E,
						ELGAMAL_REENCRYPT_FACTOR_EPRIME, EL_GAMAL_PUBLIC_KEY_E));
	}

	@Test
	@DisplayName("if the mix is null, a NullPointerException is thrown")
	void test1() {
		assertThrows(NullPointerException.class,
				() -> getReencryptedVoteFromMix.apply(null, 0,
						ELGAMAL_REENCRYPT_FACTOR_E, ELGAMAL_REENCRYPT_FACTOR_EPRIME,
						EL_GAMAL_PUBLIC_KEY_E));
	}

	@Test
	@DisplayName("if the choice factor is null, a NullPointerException is thrown")
	void test2() {
		assertThrows(NullPointerException.class,
				() -> getReencryptedVoteFromMix.apply(FROM_MIX, 0, null,
						ELGAMAL_REENCRYPT_FACTOR_EPRIME, EL_GAMAL_PUBLIC_KEY_E));
	}

	@Test
	@DisplayName("if the capability factor is null, a NullPointerException is thrown")
	void test3() {
		assertThrows(NullPointerException.class,
				() -> getReencryptedVoteFromMix.apply(FROM_MIX, 0,
						ELGAMAL_REENCRYPT_FACTOR_E, null, EL_GAMAL_PUBLIC_KEY_E));
	}

	@Test
	@DisplayName("if the key is null, a NullPointerException is thrown")
	void test4() {
		assertThrows(NullPointerException.class,
				() -> getReencryptedVoteFromMix.apply(FROM_MIX, 0,
						ELGAMAL_REENCRYPT_FACTOR_E, ELGAMAL_REENCRYPT_FACTOR_EPRIME, null));
	}

	@Test
	@DisplayName("if i is out of bound, an IndexOutOfBoundsException is thrown")
	void test5() {
		assertThrows(IndexOutOfBoundsException.class,
				() -> getReencryptedVoteFromMix.apply(FROM_MIX, 2,
						ELGAMAL_REENCRYPT_FACTOR_E, ELGAMAL_REENCRYPT_FACTOR_EPRIME,
						EL_GAMAL_PUBLIC_KEY_E));
	}

}
