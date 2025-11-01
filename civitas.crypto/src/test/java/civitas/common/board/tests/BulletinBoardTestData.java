package civitas.common.board.tests;

import civitas.bboard.server.Board;
import civitas.crypto.rsapublickey.tests.PublicKeyTestData;

public interface BulletinBoardTestData extends PublicKeyTestData {
	int NUM_VOTER_BLOCKS = 2;
	String BULLETIN_BOARD_ID = "BulletinBoardId";
	int BOARD_INDEX = 5;

	Board BULLETIN_BOARD = new Board(BULLETIN_BOARD_ID, PUBLIC_KEY_BASE64, true);
}
