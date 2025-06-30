package civitas.common.board;

public class GetContentCommitmentForBoard {
	public BoardClosedContentCommitment apply(BoardsForTabulation that,
			String boardName) {
		for (BoardClosedContentCommitment cc : that.contentComs)
			if (cc.boardName.equals(boardName))
				return cc;
		return null;
	}

}
