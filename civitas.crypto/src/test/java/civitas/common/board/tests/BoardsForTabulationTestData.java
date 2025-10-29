package civitas.common.board.tests;

import civitas.common.board.BoardClosedContentCommitment;
import civitas.common.board.BoardsForTabulation;

public interface BoardsForTabulationTestData extends BoardClosedContentCommitmentTestData {

	BoardsForTabulation BOARDS_FOR_TABULATION =
			new BoardsForTabulation(BOARD_CLOSED_CONTENT_COMMITMENTS.toArray(new BoardClosedContentCommitment[0]));
	BoardsForTabulation BOARDS_FOR_TABULATION_WITH_NULL =
			new BoardsForTabulation(new BoardClosedContentCommitment[] {null});
}
