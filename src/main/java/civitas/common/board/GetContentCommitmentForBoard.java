package civitas.common.board;

public class GetContentCommitmentForBoard {
	public BoardClosedContentCommitment contentCommitmentForBoard(
			BoardsForTabulation that, int boardIndex) {
		if (that.contentComs == null)
			return null;
		for (BoardClosedContentCommitment cc : that.contentComs) {
			try {
				if (cc != null && cc.boardIndex == boardIndex) {
					return cc;
				}
			} catch (ArrayIndexOutOfBoundsException imposs) {
			}
		}
		return null;
	}

}
