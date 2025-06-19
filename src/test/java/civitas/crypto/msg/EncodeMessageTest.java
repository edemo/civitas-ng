package civitas.crypto.msg;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import civitas.AppTestConfig;
import civitas.common.TestBase;
import civitas.crypto.CryptoException;
import civitas.crypto.votecapabilityshare.VoteCapabilityShareTestData;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = AppTestConfig.class)
public class EncodeMessageTest extends TestBase
		implements VoteCapabilityShareTestData {
	@Autowired
	EncodeMessage encodeMessage;

	@Test
	@DisplayName("encodes the message: g^msg mod p")
	void test() throws CryptoException {

		assertEquals(MESSAGE_VOTE_CAPABILITY_SHARE_ENCODED, encodeMessage
				.apply(MESSAGE_VOTE_CAPABILITY_SHARE, EL_GAMAL_PARAMETERS));
	}

}
