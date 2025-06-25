package civitas.bboard.server.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;

import java.io.IOException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.slf4j.MarkerFactory;

import civitas.bboard.common.BBPostTestData;
import civitas.common.EnvironmentState;
import civitas.common.Operation;
import civitas.common.TestBase;
import civitas.common.board.BulletinBoardTestData;

class PostServiceTest extends TestBase
		implements BulletinBoardTestData, BBPostTestData {

	@InjectMocks
	PostService postService;

	@Test
	@DisplayName("records a post to a bulletin board and returns the time of recording\n"
			+ "- checks access right\n" + "- verifies the signature\n"
			+ "- retrieves the last post for the serial and hash\n"
			+ "- computes the hash using the previous hash and the signature\n"
			+ "- records the hash , serial and current time in the post and saves it\n"
			+ "- updates the election cache\n"
			+ "- logs the transaction with its meta and board id\n")
	void test() throws IOException, SecurityException {
		assertEquals(CURRENT_TIME,
				postService.apply(BULLETIN_BOARD_ID, BoardClosedContentCommitmentMETA,
						BOARD_CLOSED_CONTENT_COMMITMENT_XML,
						BOARD_CLOSED_CONTENT_COMMITMENT_SIGNATURE));
		verify(postService.checkAccess).apply(Operation.POST,
				BOARD_CLOSED_CONTENT_COMMITMENT_SIGNATURE.getSigner(),
				BoardClosedContentCommitmentMETA + BULLETIN_BOARD_ID);
		verify(postService.verifyPublicKeySignature).apply(
				BOARD_CLOSED_CONTENT_COMMITMENT_SIGNATURE,
				BOARD_CLOSED_CONTENT_COMMITMENT_XML.getBytes());
		verify(postService.getBoardForId).apply(BULLETIN_BOARD_ID, true);
		verify(postService.updateCache).apply(BULLETIN_BOARD_ID,
				BoardClosedContentCommitmentMETA, BOARD_CLOSED_CONTENT_COMMITMENT_XML,
				CURRENT_TIME);
		verify(postService.bBPostRepository)
				.findByBbidOrderBySerialDesc(BULLETIN_BOARD_ID);
		verify(postService.cryptoHash).apply(BBPOST, CURRENT_TIME,
				BOARD_CLOSED_CONTENT_COMMITMENT_SIGNATURE);
		verify(postService.loggerService).apply(MarkerFactory.getMarker("bbs_post"),
				BoardClosedContentCommitmentMETA + BULLETIN_BOARD_ID);
		verify(postService.bBPostRepository).save(NEXT_POST);

	}

	@Test
	@DisplayName("if the signature does not check, an IllegalArgumentException is thrown")
	void test1() throws IOException, SecurityException {
		assertThrows(IllegalArgumentException.class,
				() -> postService.apply(BULLETIN_BOARD_ID,
						BoardClosedContentCommitmentMETA,
						BOARD_CLOSED_CONTENT_COMMITMENT_XML,
						BOARD_CLOSED_CONTENT_COMMITMENT_SIGNATURE_BAD));
	}

	@Test
	@DisplayName("if the signer is not authorized to post, a SecurityException is thrown")
	void test2() throws IOException, SecurityException {
		assertThrows(SecurityException.class,
				() -> postService.apply(BULLETIN_BOARD_ID,
						BoardClosedContentCommitmentMETA,
						BOARD_CLOSED_CONTENT_COMMITMENT_XML,
						BOARD_CLOSED_CONTENT_COMMITMENT_SIGNATURE_BAD_ACTOR));
	}

	@Test
	@DisplayName("if there is no previous post the hash uses only the time and signature, and the serial is 1 ")
	void test3() throws IOException, SecurityException {
		given(EnvironmentState.EMPTY_BOARD);
		assertEquals(CURRENT_TIME,
				postService.apply(BULLETIN_BOARD_ID, BoardClosedContentCommitmentMETA,
						BOARD_CLOSED_CONTENT_COMMITMENT_XML,
						BOARD_CLOSED_CONTENT_COMMITMENT_SIGNATURE));
		verify(postService.cryptoHash).apply(null, CURRENT_TIME,
				BOARD_CLOSED_CONTENT_COMMITMENT_SIGNATURE);
		verify(postService.bBPostRepository).save(FIRST_POST);
	}

}
