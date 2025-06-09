package civitas.common.board;

public class GetContentCommitmentForBoard {
	public BoardClosedContentCommitment contentCommitmentForBoard(
			BoardsForTabulation that, int boardIndex) {
		if (that.contentComs == null)
			return null;
		for (int i = 0; i < that.contentComs.length; i++) {
			try {
				BoardClosedContentCommitment cc = that.contentComs[i];
				if (cc != null && cc.boardIndex == boardIndex) {
					return cc;
				}
			} catch (ArrayIndexOutOfBoundsException imposs) {
			}
		}
		return null;
	}

}
