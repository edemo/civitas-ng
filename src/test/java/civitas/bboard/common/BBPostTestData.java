package civitas.bboard.common;

import civitas.common.electoralroll.ElectoralRollCapabilitySharesTestData;

public interface BBPostTestData extends ElectoralRollCapabilitySharesTestData {
	long TIMESTAMP = 20250711L;
	long SERIAL = 12L;
	byte[] BBPOST_HASH = "bbpost_hash".getBytes();
	BBPost BBPOST = new BBPost(BULLETIN_BOARD_ID, SERIAL, TIMESTAMP,
			META_FOR_ELECTORAL_ROLL_CAPABILITY_SHARES,
			ELECTORAL_ROLL_CAPABILITY_SHARES_XML,
			SIGNATURE_ELECTORAL_ROLL_CAPABILITY_SHARES, BBPOST_HASH);

}
