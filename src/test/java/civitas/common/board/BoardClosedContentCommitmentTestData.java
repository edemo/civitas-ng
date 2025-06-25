package civitas.common.board;

import java.util.Base64;
import java.util.List;

import civitas.common.election.ElectionTestData;

public interface BoardClosedContentCommitmentTestData
		extends ElectionTestData, BulletinBoardTestData {

	List<BoardClosedContentCommitment> BOARD_CLOSED_CONTENT_COMMITMENTS = List
			.of(0, 2, 1).stream()
			.map(
					x -> new BoardClosedContentCommitment(ELECTION_ID, "board" + x, null))
			.toList();
	String BLOCK0_META = "voterSubmission-voterBlock0";
	byte[] BLOCK0_HASH = "bbblock0 hash".getBytes();
	String BLOCK0_HASH_BASE64 = Base64.getEncoder().encodeToString(BLOCK0_HASH);
	String BLOCK1_META = "voterSubmission-voterBlock1";
	byte[] BLOCK1_HASH = "bbblock1 hash".getBytes();
	String BLOCK1_HASH_BASE64 = Base64.getEncoder().encodeToString(BLOCK1_HASH);

	BoardClosedContentCommitment BOARD_CLOSED_CONTENT_COMMITMENT = new BoardClosedContentCommitment(
			ELECTION_ID, BULLETIN_BOARD_ID,
			new String[] { BLOCK0_HASH_BASE64, BLOCK1_HASH_BASE64 });

	String BOARD_CLOSED_CONTENT_COMMITMENT_XML = "mock BOARD_CLOSED_CONTENT_COMMITMENT_XML";

}
