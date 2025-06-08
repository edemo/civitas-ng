package civitas.crypto.msg;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import civitas.common.TestBase;
import civitas.crypto.CryptoException;
import civitas.crypto.votecapabilityshare.VoteCapabilityShareTestData;
import civitas.util.Use;

public class EncodeMessageTest extends TestBase
		implements VoteCapabilityShareTestData {
	@Use
	EncodeMessage encodeMessage;

	@Test
	@DisplayName("encodes the message: g^msg mod p")
	void test() throws CryptoException {

		assertEquals(MESSAGE_VOTE_CAPABILITY_SHARE_ENCODED, encodeMessage
				.apply(MESSAGE_VOTE_CAPABILITY_SHARE, EL_GAMAL_PARAMETERS));
	}

}
