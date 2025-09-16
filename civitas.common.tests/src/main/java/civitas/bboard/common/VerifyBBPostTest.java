package civitas.bboard.common;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import org.bouncycastle.crypto.CryptoException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import com.fasterxml.jackson.core.JsonProcessingException;

import civitas.common.RandomAwareTestBase;
import civitas.common.electoralroll.ElectoralRollCapabilitySharesTestData;
import civitas.crypto.CryptoError;
import civitas.crypto.rsapublickey.PublicKeyTestData;

class VerifyBBPostTest extends RandomAwareTestBase implements BBPostTestData,
		ElectoralRollCapabilitySharesTestData, PublicKeyTestData {

	@InjectMocks
	VerifyBBPost verifyBBPost;

	@Test
	@DisplayName("verifies if the signature matches the xml form of the message. returns true if it does"
			+ "- converts the message to xml" + "- computes the hash of the xml"
			+ "- verifies that the signature is the signature of the hash using the key")
	void test() throws JsonProcessingException, NoSuchAlgorithmException,
			InvalidKeySpecException, CryptoError, UnsupportedEncodingException,
			CryptoException {
		assertTrue(verifyBBPost.apply(BBPOST));
		verify(verifyBBPost.cryptoHash)
				.apply(ELECTORAL_ROLL_CAPABILITY_SHARES_XML.getBytes());
		verify(verifyBBPost.verifyPublicKeySignature).apply(BBPOST.sig,
				ELECTORAL_ROLL_CAPABILITY_SHARES_XML_HASH);
	}

	@Test
	@DisplayName("if the signature does not verify, returns false")
	void test1() throws JsonProcessingException, NoSuchAlgorithmException,
			InvalidKeySpecException, CryptoError, UnsupportedEncodingException,
			CryptoException {
		assertFalse(verifyBBPost.apply(BBPOST_BAD_SIG));
	}

}
