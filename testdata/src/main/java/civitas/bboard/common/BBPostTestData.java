package civitas.bboard.common;

import java.util.List;

import civitas.common.CommonConstants;
import civitas.common.board.BoardClosedContentCommitmentTestData;
import civitas.common.electoralroll.ElectoralRollCapabilitySharesTestData;
import civitas.crypto.signature.SignatureTestData;

public interface BBPostTestData extends ElectoralRollCapabilitySharesTestData,
		BoardClosedContentCommitmentTestData, SignatureTestData, CommonConstants {
	long SERIAL = 12L;
	public static final String BBPOST_HASH_STRINGBASE = "bbpost_hash";
	byte[] BBPOST_HASH = BBPOST_HASH_STRINGBASE.getBytes();
	BBPost BBPOST = new BBPost(BULLETIN_BOARD_ID, SERIAL, CURRENT_TIME,
			META_FOR_ELECTORAL_ROLL_CAPABILITY_SHARES,
			ELECTORAL_ROLL_CAPABILITY_SHARES_XML,
			SIGNATURE_ELECTORAL_ROLL_CAPABILITY_SHARES, BBPOST_HASH);
	BBPost BBPOST_BAD_SIG = new BBPost(BULLETIN_BOARD_ID, SERIAL, CURRENT_TIME,
			META_FOR_ELECTORAL_ROLL_CAPABILITY_SHARES,
			ELECTORAL_ROLL_CAPABILITY_SHARES_XML,
			SIGNATURE_ELECTORAL_ROLL_CAPABILITY_SHARES_BAD, BBPOST_HASH);
	BBPost NEXT_POST = new BBPost(BULLETIN_BOARD_ID, SERIAL + 1, CURRENT_TIME,
			BoardClosedContentCommitmentMETA, BOARD_CLOSED_CONTENT_COMMITMENT_XML,
			BOARD_CLOSED_CONTENT_COMMITMENT_SIGNATURE,
			BOARD_CLOSED__CONTENT_COMMITMENT_SIGNATURE_HASH);
	BBPost FIRST_POST = new BBPost(BULLETIN_BOARD_ID, 1L, CURRENT_TIME,
			BoardClosedContentCommitmentMETA, BOARD_CLOSED_CONTENT_COMMITMENT_XML,
			BOARD_CLOSED_CONTENT_COMMITMENT_SIGNATURE,
			BOARD_CLOSED__CONTENT_COMMITMENT_SIGNATURE_HASH_NOPREV);

	List<BBPost> BBPOSTS = List.of(BBPOST);

}
