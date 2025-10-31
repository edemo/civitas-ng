package civitas.bboard.server.controllers.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;

import java.io.IOException;
import java.math.BigInteger;

import org.bouncycastle.crypto.CryptoException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.slf4j.MarkerFactory;

import civitas.bboard.common.BBPostRepository;
import civitas.bboard.common.tests.BBPostTestData;
import civitas.bboard.server.GetBoardForId;
import civitas.bboard.server.controllers.CommunicableException;
import civitas.bboard.server.controllers.PostController;
import civitas.bboard.server.controllers.PostDTO;
import civitas.bboard.server.electioncache.UpdateCache;
import civitas.common.CheckAccess;
import civitas.common.LoggerService;
import civitas.common.Operation;
import civitas.common.board.tests.BulletinBoardTestData;
import civitas.common.tests.EnvironmentState;
import civitas.common.tests.RandomAwareTestBase;
import civitas.crypto.messagedigest.CryptoHash;
import civitas.crypto.rsapublickey.VerifyPublicKeySignature;
import jakarta.xml.bind.JAXBException;

class PostControllerTest extends RandomAwareTestBase implements BulletinBoardTestData, BBPostTestData {

	@InjectMocks
	PostController postController;

	@Mock
	CheckAccess checkAccess;

	@Mock
	GetBoardForId getBoardForId;

	@Mock
	UpdateCache updateCache;

	@Mock
	BBPostRepository bBPostRepository;

	@Mock
	CryptoHash cryptoHash;

	@Mock
	LoggerService logger;

	@Mock
	VerifyPublicKeySignature verifyPublicKeySignature;

	@Test
	@DisplayName(
			"""
			records a post to a bulletin board and returns the time of recording
			- checks access right
			- verifies the signature
			- retrieves the last post for the serial and hash
			- computes the hash using the previous hash and the signature
			- records the hash, serial and current time in the post and saves it
			- updates the election cache
			- logs the transaction with its meta and board id
			""")
	void test() throws CommunicableException, JAXBException, IOException, CryptoException {
		assertEquals(
				CURRENT_TIME,
				postController.apply(
						BULLETIN_BOARD_ID,
						new PostDTO(
								BOARD_CLOSED_CONTENT_COMMITMENT_META,
								BOARD_CLOSED_CONTENT_COMMITMENT_XML,
								BOARD_CLOSED_CONTENT_COMMITMENT_SIGNATURE)));
		verify(checkAccess)
				.apply(
						Operation.POST,
						BOARD_CLOSED_CONTENT_COMMITMENT_SIGNATURE.getSignerPubKey(),
						BOARD_CLOSED_CONTENT_COMMITMENT_META + BULLETIN_BOARD_ID);
		verify(verifyPublicKeySignature)
				.apply(BOARD_CLOSED_CONTENT_COMMITMENT_SIGNATURE, BOARD_CLOSED_CONTENT_COMMITMENT_XML.getBytes());
		verify(getBoardForId).apply(BULLETIN_BOARD_ID, true);
		verify(updateCache)
				.apply(
						BULLETIN_BOARD_ID,
						BOARD_CLOSED_CONTENT_COMMITMENT_META,
						BOARD_CLOSED_CONTENT_COMMITMENT_XML,
						CURRENT_TIME);
		verify(bBPostRepository).findByBbidOrderBySerialDesc(BULLETIN_BOARD_ID);
		verify(cryptoHash)
				.apply(
						BBPOST.hash,
						BigInteger.valueOf(CURRENT_TIME).toByteArray(),
						BOARD_CLOSED_CONTENT_COMMITMENT_SIGNATURE.signatureBytes);
		verify(logger)
				.apply(MarkerFactory.getMarker("bbs_post"), BOARD_CLOSED_CONTENT_COMMITMENT_META + BULLETIN_BOARD_ID);
		verify(bBPostRepository).save(NEXT_POST);
	}

	@Test
	@DisplayName("if the signature does not check, a CommunicableException is thrown")
	void test1() {
		assertThrows(
				CommunicableException.class,
				() -> postController.apply(
						BULLETIN_BOARD_ID,
						new PostDTO(
								BOARD_CLOSED_CONTENT_COMMITMENT_META,
								BOARD_CLOSED_CONTENT_COMMITMENT_XML,
								BOARD_CLOSED_CONTENT_COMMITMENT_SIGNATURE_BAD)));
	}

	@Test
	@DisplayName("if the signer is not authorized to post, a SecurityException is thrown")
	void test2() {
		assertThrows(
				SecurityException.class,
				() -> postController.apply(
						BULLETIN_BOARD_ID,
						new PostDTO(
								BOARD_CLOSED_CONTENT_COMMITMENT_META,
								BOARD_CLOSED_CONTENT_COMMITMENT_XML,
								BOARD_CLOSED_CONTENT_COMMITMENT_SIGNATURE_BAD_ACTOR)));
	}

	@Test
	@DisplayName("if there is no previous post the hash uses only the time and signature, and the serial is 1 ")
	void test3() throws CommunicableException {
		given(EnvironmentState.EMPTY_BOARD);
		Long actual = postController.apply(
				BULLETIN_BOARD_ID,
				new PostDTO(
						BOARD_CLOSED_CONTENT_COMMITMENT_META,
						BOARD_CLOSED_CONTENT_COMMITMENT_XML,
						BOARD_CLOSED_CONTENT_COMMITMENT_SIGNATURE));
		verify(cryptoHash)
				.apply(
						new byte[0],
						BigInteger.valueOf(CURRENT_TIME).toByteArray(),
						BOARD_CLOSED_CONTENT_COMMITMENT_SIGNATURE.signatureBytes);
		verify(bBPostRepository).save(FIRST_POST);
		assertEquals(CURRENT_TIME, actual);
	}
}
