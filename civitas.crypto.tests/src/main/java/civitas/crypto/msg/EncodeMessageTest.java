package civitas.crypto.msg;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import civitas.crypto.CryptoException;
import civitas.crypto.votecapabilityshare.VoteCapabilityShareTestData;
import io.github.magwas.testing.TestBase;

class EncodeMessageTest extends TestBase implements VoteCapabilityShareTestData {
	@InjectMocks
	EncodeMessage encodeMessage;

	@Test
	@DisplayName("encodes the message: g^msg mod p")
	void test() throws CryptoException {

		assertEquals(
				MESSAGE_VOTE_CAPABILITY_SHARE_ENCODED,
				encodeMessage.apply(MESSAGE_VOTE_CAPABILITY_SHARE, EL_GAMAL_PARAMETERS));
	}

	@Test
	@DisplayName("encodeMessage with int parameter stores the message converted to bigint and encrypted")
	void test1() throws CryptoException {
		assertEquals(BIGINT_G.modPow(SOME_INT_BIG, BIGINT_P), encodeMessage.apply(SOME_INT, EL_GAMAL_PARAMETERS));
	}

	@Test
	@DisplayName("encodeMessage with String parameter stores the message encrypted")
	void test1_1() throws CryptoException {
		assertEquals(
				BIGINT_G.modPow(SOMESTRING_BIGINT, BIGINT_P), encodeMessage.apply(SOMESTRING, EL_GAMAL_PARAMETERS));
	}

	@Test
	@DisplayName("empty string cannot be used in encodeMessage")
	void test1_1_1() throws CryptoException {
		assertThrows(CryptoException.class, () -> encodeMessage.apply("", EL_GAMAL_PARAMETERS));
	}

	@Test
	@DisplayName("encodeMessage with BigInt parameter stores the message encrypted")
	void test1_2() throws CryptoException {
		assertEquals(BIGINT_G.modPow(BIGINT_A, BIGINT_P), encodeMessage.apply(BIGINT_A, EL_GAMAL_PARAMETERS));
	}
}
