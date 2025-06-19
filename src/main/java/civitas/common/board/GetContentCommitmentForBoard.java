package civitas.common.board;

public class GetContentCommitmentForBoard {
	public BoardClosedContentCommitment apply(BoardsForTabulation that,
			int boardIndex) {
		for (BoardClosedContentCommitment cc : that.contentComs)
			if (cc.boardIndex == boardIndex)
				return cc;
		return null;
	}

}
