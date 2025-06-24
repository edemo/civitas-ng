package civitas.bboard.server.services;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;

import java.io.IOException;
import java.security.spec.InvalidKeySpecException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import civitas.common.EnvironmentState;
import civitas.common.TestBase;
import civitas.common.board.BoardClosedContentCommitment;
import civitas.common.board.BulletinBoardTestData;
import civitas.common.election.ElectionTestData;
import civitas.crypto.publickeymsg.PublicKeyMsg;
import civitas.crypto.rsaprivatekey.KeySpecMatcher;
import civitas.crypto.signature.SignatureTestData;

class CloseBoardServiceTest extends TestBase
		implements BulletinBoardTestData, ElectionTestData, SignatureTestData {

	@InjectMocks
	CloseBoardService closeBoardService;

	@Test
	@DisplayName("closes the board\n"
			+ "- verifies the signature based on the owner key stored for the bulletin board\n"
			+ "- marks the board closed\n"
			+ "- calculates the BoardClosedContentCommitment for all voter block\n"
			+ "- posts the BoardClosedContentCommitment for the election master")
	void test() throws InvalidKeySpecException, IOException {
		try {
			closeBoardService.apply(BULLETIN_BOARD_ID, ELECTION_ID, NUM_VOTER_BLOCKS,
					SIGNATURE_OF_AUTH_NONCE_WITH_KEY);
		} catch (Exception e) {
			// TODO: handle exception
		}
		verify(closeBoardService.getBoardForId).apply(BULLETIN_BOARD_ID, true);
		verify(closeBoardService.cryptoBase.publicKeyFactory)
				.generatePublic(argThat(new KeySpecMatcher(KEYSPEC_PUBLIC)));
		verify(closeBoardService.verifyPublicKeySignature).apply(PUBLIC_KEY,
				SIGNATURE_OF_AUTH_NONCE_WITH_KEY, new PublicKeyMsg(BULLETIN_BOARD_ID));
		verify(closeBoardService.boardRepository).save(BULLETIN_BOARD);
		verify(closeBoardService.cryptoHash).apply(BULLETIN_BOARD_ID.getBytes(),
				"voterSubmission-voterBlock0".getBytes());
		verify(closeBoardService.cryptoHash).apply(BULLETIN_BOARD_ID.getBytes(),
				"voterSubmission-voterBlock1".getBytes());
		verify(closeBoardService.restClient.restTemplate).postForObject(
				ELECTION_ID.uriBase + "/post",
				new BoardClosedContentCommitment(ELECTION_ID, BULLETIN_BOARD_ID,
						new String[] { BLOCK0_HASH_BASE64, BLOCK1_HASH_BASE64 }),
				Boolean.class);
	}

	@Test
	@DisplayName("if the signature does not verify, does nothing")
	void test2() throws InvalidKeySpecException, IOException {
		closeBoardService.apply(BULLETIN_BOARD_ID, ELECTION_ID, NUM_VOTER_BLOCKS,
				SIGNATURE_OF_AUTH_NONCE_WITH_KEY2);
		verify(closeBoardService.boardRepository, times(0)).save(BULLETIN_BOARD);
		verifyNoInteractions(closeBoardService.cryptoHash);
		verifyNoInteractions(closeBoardService.restClient.restTemplate);
	}

	@Test
	@DisplayName("if the election is null, a NullPointerException is thrown")
	void test3() throws InvalidKeySpecException, IOException {
		assertThrows(NullPointerException.class,
				() -> closeBoardService.apply(BULLETIN_BOARD_ID, null, NUM_VOTER_BLOCKS,
						SIGNATURE_OF_AUTH_NONCE_WITH_KEY));
	}

	@Test
	@DisplayName("if the signature is null, a NullPointerException is thrown")
	void test4() throws InvalidKeySpecException, IOException {
		assertThrows(NullPointerException.class, () -> closeBoardService
				.apply(BULLETIN_BOARD_ID, ELECTION_ID, NUM_VOTER_BLOCKS, null));
	}

	@Test
	@DisplayName("if the number of voter blocks is negative, an IllegalArgumentException is thrown")
	void test5() throws InvalidKeySpecException, IOException {
		assertThrows(IllegalArgumentException.class,
				() -> closeBoardService.apply(BULLETIN_BOARD_ID, ELECTION_ID, -1,
						SIGNATURE_OF_AUTH_NONCE_WITH_KEY));
	}

	@Test
	@DisplayName("if the number of voter blocks is zero, the BoardClosedContentCommitment posted has empty hashes")
	void test6() throws InvalidKeySpecException, IOException {
		closeBoardService.apply(BULLETIN_BOARD_ID, ELECTION_ID, 0,
				SIGNATURE_OF_AUTH_NONCE_WITH_KEY);
		verify(closeBoardService.restClient.restTemplate).postForObject(
				ELECTION_ID.uriBase + "/post", new BoardClosedContentCommitment(
						ELECTION_ID, BULLETIN_BOARD_ID, new String[] {}),
				Boolean.class);
	}

	@Test
	@DisplayName("if the election server is unreachable, an IOException is thrown and the board is not closed")
	void test7() throws InvalidKeySpecException, IOException {
		given(EnvironmentState.ELECTION_SERVER_IS_UNREACHEABLE);
		assertThrows(IOException.class,
				() -> closeBoardService.apply(BULLETIN_BOARD_ID, ELECTION_ID, 0,
						SIGNATURE_OF_AUTH_NONCE_WITH_KEY));
		verify(closeBoardService.boardRepository, times(0)).save(BULLETIN_BOARD);
	}

}
