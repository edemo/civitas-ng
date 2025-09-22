package civitas.bboard.server.controllers;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;

import org.bouncycastle.crypto.CryptoException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import civitas.common.EnvironmentState;
import civitas.common.RandomAwareTestBase;
import civitas.common.board.BoardClosedContentCommitment;
import civitas.common.board.BoardClosedContentCommitmentTestData;
import civitas.crypto.signature.SignatureTestData;

class CloseBoardControllerTest extends RandomAwareTestBase
		implements BoardClosedContentCommitmentTestData, SignatureTestData {

	@InjectMocks
	CloseBoardController closeBoardController;

	@Test
	@DisplayName(
			"""
			closes the board
			- verifies the signature based on the owner key stored for the bulletin board
			- marks the board closed
			- calculates the BoardClosedContentCommitment for all voter block
			- posts the BoardClosedContentCommitment for the election master
			""")
	void test()
			throws InvalidKeySpecException, IOException, NoSuchAlgorithmException, CommunicableException,
					CryptoException {
		closeBoardController.apply(BULLETIN_BOARD_ID, ELECTION_ID, NUM_VOTER_BLOCKS, SIGNATURE_OF_AUTH_NONCE_WITH_KEY);
		verify(closeBoardController.getBoardForId).apply(BULLETIN_BOARD_ID, true);
		verify(closeBoardController.verifyPublicKeySignature)
				.apply(SIGNATURE_OF_AUTH_NONCE_WITH_KEY, PUBLIC_KEY, BULLETIN_BOARD_ID);
		verify(closeBoardController.boardRepository).save(BULLETIN_BOARD);
		verify(closeBoardController.cryptoHash)
				.apply(BULLETIN_BOARD_ID.getBytes(), "voterSubmission-voterBlock0".getBytes());
		verify(closeBoardController.cryptoHash)
				.apply(BULLETIN_BOARD_ID.getBytes(), "voterSubmission-voterBlock1".getBytes());
		verify(closeBoardController.getRestTemplate.restTemplate)
				.postForObject(ELECTION_ID.uriBase() + "/post", BOARD_CLOSED_CONTENT_COMMITMENT, Boolean.class);
	}

	@Test
	@DisplayName("if the signature does not verify, does nothing")
	void test2() throws InvalidKeySpecException, IOException, NoSuchAlgorithmException, CommunicableException {
		closeBoardController.apply(BULLETIN_BOARD_ID, ELECTION_ID, NUM_VOTER_BLOCKS, SIGNATURE_OF_AUTH_NONCE_WITH_KEY2);
		verify(closeBoardController.boardRepository, times(0)).save(BULLETIN_BOARD);
		verifyNoInteractions(closeBoardController.cryptoHash);
		verifyNoInteractions(closeBoardController.getRestTemplate.restTemplate);
	}

	@Test
	@DisplayName("if the election is null, a NullPointerException is thrown")
	void test3() throws InvalidKeySpecException, IOException {
		assertThrows(
				NullPointerException.class,
				() -> closeBoardController.apply(
						BULLETIN_BOARD_ID, null, NUM_VOTER_BLOCKS, SIGNATURE_OF_AUTH_NONCE_WITH_KEY));
	}

	@Test
	@DisplayName("if the signature is null, a NullPointerException is thrown")
	void test4() throws InvalidKeySpecException, IOException {
		assertThrows(
				NullPointerException.class,
				() -> closeBoardController.apply(BULLETIN_BOARD_ID, ELECTION_ID, NUM_VOTER_BLOCKS, null));
	}

	@Test
	@DisplayName("if the number of voter blocks is negative, an IllegalArgumentException is thrown")
	void test5() throws InvalidKeySpecException, IOException {
		assertThrows(
				IllegalArgumentException.class,
				() -> closeBoardController.apply(BULLETIN_BOARD_ID, ELECTION_ID, -1, SIGNATURE_OF_AUTH_NONCE_WITH_KEY));
	}

	@Test
	@DisplayName("if the number of voter blocks is zero, the BoardClosedContentCommitment posted has empty hashes")
	void test6() throws InvalidKeySpecException, IOException, NoSuchAlgorithmException, CommunicableException {
		closeBoardController.apply(BULLETIN_BOARD_ID, ELECTION_ID, 0, SIGNATURE_OF_AUTH_NONCE_WITH_KEY);
		verify(closeBoardController.getRestTemplate.restTemplate)
				.postForObject(
						ELECTION_ID.uriBase() + "/post",
						new BoardClosedContentCommitment(ELECTION_ID, BULLETIN_BOARD_ID, new ArrayList<>()),
						Boolean.class);
	}

	@Test
	@DisplayName("if the election server is unreachable, an IOException is thrown and the board is not closed")
	void test7() throws InvalidKeySpecException, IOException {
		given(EnvironmentState.ELECTION_SERVER_IS_UNREACHEABLE);
		assertThrows(
				IOException.class,
				() -> closeBoardController.apply(BULLETIN_BOARD_ID, ELECTION_ID, 0, SIGNATURE_OF_AUTH_NONCE_WITH_KEY));
		verify(closeBoardController.boardRepository, times(0)).save(BULLETIN_BOARD);
	}
}
