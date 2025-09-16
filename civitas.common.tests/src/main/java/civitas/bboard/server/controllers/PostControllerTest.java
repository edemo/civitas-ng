package civitas.bboard.server.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;

import java.io.IOException;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import org.bouncycastle.crypto.CryptoException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.slf4j.MarkerFactory;

import civitas.bboard.common.BBPostTestData;
import civitas.common.EnvironmentState;
import civitas.common.Operation;
import civitas.common.RandomAwareTestBase;
import civitas.common.board.BulletinBoardTestData;
import civitas.crypto.CryptoError;
import jakarta.xml.bind.JAXBException;

public class PostControllerTest extends RandomAwareTestBase
		implements BulletinBoardTestData, BBPostTestData {

	@InjectMocks
	PostController postController;

	@Test
	@DisplayName("records a post to a bulletin board and returns the time of recording\n"
			+ "- checks access right\n" + "- verifies the signature\n"
			+ "- retrieves the last post for the serial and hash\n"
			+ "- computes the hash using the previous hash and the signature\n"
			+ "- records the hash , serial and current time in the post and saves it\n"
			+ "- updates the election cache\n"
			+ "- logs the transaction with its meta and board id\n")
	void test() throws CommunicableException, JAXBException, IOException,
			NoSuchAlgorithmException, InvalidKeySpecException, CryptoError,
			CryptoException {
		assertEquals(CURRENT_TIME,
				postController.apply(BULLETIN_BOARD_ID,
						new PostDTO(BoardClosedContentCommitmentMETA,
								BOARD_CLOSED_CONTENT_COMMITMENT_XML,
								BOARD_CLOSED_CONTENT_COMMITMENT_SIGNATURE)));
		verify(postController.checkAccess).apply(Operation.POST,
				BOARD_CLOSED_CONTENT_COMMITMENT_SIGNATURE.getSignerPubKey(),
				BoardClosedContentCommitmentMETA + BULLETIN_BOARD_ID);
		verify(postController.verifyPublicKeySignature).apply(
				BOARD_CLOSED_CONTENT_COMMITMENT_SIGNATURE,
				BOARD_CLOSED_CONTENT_COMMITMENT_XML.getBytes());
		verify(postController.getBoardForId).apply(BULLETIN_BOARD_ID, true);
		verify(postController.updateCache).apply(BULLETIN_BOARD_ID,
				BoardClosedContentCommitmentMETA, BOARD_CLOSED_CONTENT_COMMITMENT_XML,
				CURRENT_TIME);
		verify(postController.bBPostRepository)
				.findByBbidOrderBySerialDesc(BULLETIN_BOARD_ID);
		verify(postController.cryptoHash).apply(BBPOST.hash,
				BigInteger.valueOf(CURRENT_TIME).toByteArray(),
				BOARD_CLOSED_CONTENT_COMMITMENT_SIGNATURE.signatureBytes);
		verify(postController.loggerController).apply(
				MarkerFactory.getMarker("bbs_post"),
				BoardClosedContentCommitmentMETA + BULLETIN_BOARD_ID);
		verify(postController.bBPostRepository).save(NEXT_POST);

	}

	@Test
	@DisplayName("if the signature does not check, a CommunicableException is thrown")
	void test1() throws IOException, SecurityException {
		assertThrows(CommunicableException.class,
				() -> postController.apply(BULLETIN_BOARD_ID,
						new PostDTO(BoardClosedContentCommitmentMETA,
								BOARD_CLOSED_CONTENT_COMMITMENT_XML,
								BOARD_CLOSED_CONTENT_COMMITMENT_SIGNATURE_BAD)));
	}

	@Test
	@DisplayName("if the signer is not authorized to post, a SecurityException is thrown")
	void test2() throws IOException, SecurityException {
		assertThrows(SecurityException.class,
				() -> postController.apply(BULLETIN_BOARD_ID,
						new PostDTO(BoardClosedContentCommitmentMETA,
								BOARD_CLOSED_CONTENT_COMMITMENT_XML,
								BOARD_CLOSED_CONTENT_COMMITMENT_SIGNATURE_BAD_ACTOR)));
	}

	@Test
	@DisplayName("if there is no previous post the hash uses only the time and signature, and the serial is 1 ")
	void test3() throws CommunicableException {
		given(EnvironmentState.EMPTY_BOARD);
		Long actual = postController.apply(BULLETIN_BOARD_ID,
				new PostDTO(BoardClosedContentCommitmentMETA,
						BOARD_CLOSED_CONTENT_COMMITMENT_XML,
						BOARD_CLOSED_CONTENT_COMMITMENT_SIGNATURE));
		verify(postController.cryptoHash).apply(new byte[0],
				BigInteger.valueOf(CURRENT_TIME).toByteArray(),
				BOARD_CLOSED_CONTENT_COMMITMENT_SIGNATURE.signatureBytes);
		verify(postController.bBPostRepository).save(FIRST_POST);
		assertEquals(CURRENT_TIME, actual);
	}

}
