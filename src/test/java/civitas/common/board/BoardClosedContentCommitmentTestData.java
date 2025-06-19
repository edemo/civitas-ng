package civitas.common.board;

import java.util.List;

import civitas.common.election.ElectionTestData;

public interface BoardClosedContentCommitmentTestData extends ElectionTestData {

	List<BoardClosedContentCommitment> BOARD_CLOSED_CONTENT_COMMITMENTS = List
			.of(0, 2, 1).stream()
			.map(x -> new BoardClosedContentCommitment(ELECTION_ID, x, null))
			.toList();

}
