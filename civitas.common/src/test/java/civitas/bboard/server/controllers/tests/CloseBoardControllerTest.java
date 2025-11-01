package civitas.bboard.server.controllers.tests;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;

import java.io.IOException;
import java.util.ArrayList;

import org.bouncycastle.crypto.CryptoException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import civitas.bboard.server.BoardRepository;
import civitas.bboard.server.GetBoardForId;
import civitas.bboard.server.controllers.CloseBoardController;
import civitas.bboard.server.controllers.CommunicableException;
import civitas.common.board.BoardClosedContentCommitment;
import civitas.common.board.tests.BoardClosedContentCommitmentTestData;
import civitas.common.tests.EnvironmentState;
import civitas.common.tests.RandomAwareTestBase;
import civitas.crypto.messagedigest.CryptoHash;
import civitas.crypto.rsapublickey.VerifyPublicKeySignature;
import civitas.crypto.signature.tests.SignatureTestData;

class CloseBoardControllerTest extends RandomAwareTestBase
		implements BoardClosedContentCommitmentTestData, SignatureTestData {

	@InjectMocks
	CloseBoardController closeBoardController;

	@Mock
	GetBoardForId getBoardForId;

	@Mock
	VerifyPublicKeySignature verifyPublicKeySignature;

	@Mock
	BoardRepository boardRepository;

	@Mock
	CryptoHash cryptoHash;

	@Test
	@DisplayName(
			"""
			closes the board
			- verifies the signature based on the owner key stored for the bulletin board
			- marks the board closed
			- calculates the BoardClosedContentCommitment for all voter block
			- posts the BoardClosedContentCommitment for the election master
			""")
	void test() throws CommunicableException, CryptoException {
		closeBoardController.apply(BULLETIN_BOARD_ID, ELECTION_ID, NUM_VOTER_BLOCKS, SIGNATURE_OF_AUTH_NONCE_WITH_KEY);
		verify(getBoardForId).apply(BULLETIN_BOARD_ID, true);
		verify(verifyPublicKeySignature).apply(SIGNATURE_OF_AUTH_NONCE_WITH_KEY, PUBLIC_KEY, BULLETIN_BOARD_ID);
		verify(boardRepository).save(BULLETIN_BOARD);
		verify(cryptoHash).apply(BULLETIN_BOARD_ID.getBytes(), "voterSubmission-voterBlock0".getBytes());
		verify(cryptoHash).apply(BULLETIN_BOARD_ID.getBytes(), "voterSubmission-voterBlock1".getBytes());
		verify(GetRestTemplateStub.restTemplate)
				.postForObject(ELECTION_ID.uriBase() + "/post", BOARD_CLOSED_CONTENT_COMMITMENT, Boolean.class);
	}

	@Test
	@DisplayName("if the signature does not verify, does nothing")
	void test2() throws CommunicableException {
		closeBoardController.apply(BULLETIN_BOARD_ID, ELECTION_ID, NUM_VOTER_BLOCKS, SIGNATURE_OF_AUTH_NONCE_WITH_KEY2);
		verify(boardRepository, times(0)).save(BULLETIN_BOARD);
		verifyNoInteractions(cryptoHash);
		verifyNoInteractions(GetRestTemplateStub.restTemplate);
	}

	@Test
	@DisplayName("if the election is null, a NullPointerException is thrown")
	void test3() {
		assertThrows(
				NullPointerException.class,
				() -> closeBoardController.apply(
						BULLETIN_BOARD_ID, null, NUM_VOTER_BLOCKS, SIGNATURE_OF_AUTH_NONCE_WITH_KEY));
	}

	@Test
	@DisplayName("if the signature is null, a NullPointerException is thrown")
	void test4() {
		assertThrows(
				NullPointerException.class,
				() -> closeBoardController.apply(BULLETIN_BOARD_ID, ELECTION_ID, NUM_VOTER_BLOCKS, null));
	}

	@Test
	@DisplayName("if the number of voter blocks is negative, an IllegalArgumentException is thrown")
	void test5() {
		assertThrows(
				IllegalArgumentException.class,
				() -> closeBoardController.apply(BULLETIN_BOARD_ID, ELECTION_ID, -1, SIGNATURE_OF_AUTH_NONCE_WITH_KEY));
	}

	@Test
	@DisplayName("if the number of voter blocks is zero, the BoardClosedContentCommitment posted has empty hashes")
	void test6() throws CommunicableException {
		closeBoardController.apply(BULLETIN_BOARD_ID, ELECTION_ID, 0, SIGNATURE_OF_AUTH_NONCE_WITH_KEY);
		verify(GetRestTemplateStub.restTemplate)
				.postForObject(
						ELECTION_ID.uriBase() + "/post",
						new BoardClosedContentCommitment(ELECTION_ID, BULLETIN_BOARD_ID, new ArrayList<>()),
						Boolean.class);
	}

	@Test
	@DisplayName("if the election server is unreachable, an IOException is thrown and the board is not closed")
	void test7() {
		given(EnvironmentState.ELECTION_SERVER_IS_UNREACHEABLE);
		assertThrows(
				IOException.class,
				() -> closeBoardController.apply(BULLETIN_BOARD_ID, ELECTION_ID, 0, SIGNATURE_OF_AUTH_NONCE_WITH_KEY));
		verify(boardRepository, times(0)).save(BULLETIN_BOARD);
	}
}
