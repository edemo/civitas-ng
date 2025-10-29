package civitas.bboard.common.tests;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;

import org.bouncycastle.crypto.CryptoException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import civitas.bboard.common.VerifyBBPost;
import civitas.common.electoralroll.tests.ElectoralRollCapabilitySharesTestData;
import civitas.common.tests.RandomAwareTestBase;
import civitas.crypto.messagedigest.CryptoHash;
import civitas.crypto.rsapublickey.VerifyPublicKeySignature;
import civitas.crypto.rsapublickey.tests.PublicKeyTestData;
import io.github.magwas.konveyor.testing.TestUtil;

class VerifyBBPostTest extends RandomAwareTestBase
		implements BBPostTestData, ElectoralRollCapabilitySharesTestData, PublicKeyTestData {

	@InjectMocks
	VerifyBBPost verifyBBPost;

	@Test
	@DisplayName("verifies if the signature matches the xml form of the message. returns true if it does"
			+ "- converts the message to xml" + "- computes the hash of the xml"
			+ "- verifies that the signature is the signature of the hash using the key")
	void test() throws CryptoException, IllegalAccessException {
		assertTrue(verifyBBPost.apply(BBPOST));
		verify(TestUtil.dependency(verifyBBPost, CryptoHash.class))
				.apply(ELECTORAL_ROLL_CAPABILITY_SHARES_XML.getBytes());
		verify(TestUtil.dependency(verifyBBPost, VerifyPublicKeySignature.class))
				.apply(BBPOST.sig, ELECTORAL_ROLL_CAPABILITY_SHARES_XML_HASH);
	}

	@Test
	@DisplayName("if the signature does not verify, returns false")
	void test1() throws CryptoException {
		assertFalse(verifyBBPost.apply(BBPOST_BAD_SIG));
	}
}
