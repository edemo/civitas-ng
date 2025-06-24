package civitas.common.board;

import java.util.Base64;

import civitas.bboard.server.Board;
import civitas.crypto.rsapublickey.PublicKeyTestData;

public interface BulletinBoardTestData extends PublicKeyTestData {
	int NUM_VOTER_BLOCKS = 2;
	String BULLETIN_BOARD_ID = "BulletinBoardId";
	int BOARD_INDEX = 5;
	String BLOCK0_META = "voterSubmission-voterBlock0";
	byte[] BLOCK0_HASH = "bbblock0 hash".getBytes();
	String BLOCK0_HASH_BASE64 = Base64.getEncoder().encodeToString(BLOCK0_HASH);
	String BLOCK1_META = "voterSubmission-voterBlock1";
	byte[] BLOCK1_HASH = "bbblock1 hash".getBytes();
	String BLOCK1_HASH_BASE64 = Base64.getEncoder().encodeToString(BLOCK1_HASH);

	Board BULLETIN_BOARD = new Board(BULLETIN_BOARD_ID, KEY_NAME,
			PUBLIC_KEY_BASE64, true);

}
