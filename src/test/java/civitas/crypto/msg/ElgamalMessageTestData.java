package civitas.crypto.msg;

import civitas.common.Util;
import civitas.crypto.publickey.ElGamalPublicKeyCTestData;
import civitas.util.CivitasBigInteger;

public interface ElgamalMessageTestData extends ElGamalPublicKeyCTestData {

	public static final String VOTE = "3,(2,5),0,4,1";

	CivitasBigInteger MESSAGE_VOTE_CAPABILITY_SHARE = new CivitasBigInteger(
			VOTE.getBytes());

	CivitasBigInteger MESSAGE_VOTE_CAPABILITY_SHARE_ENCODED = BIGINT_G
			.modPow(MESSAGE_VOTE_CAPABILITY_SHARE, BIGINT_P);
	ElGamalMsgC EL_GAMAL_MESSAGE_VOTE_CAPABILITY_SHARE = new ElGamalMsgC(
			MESSAGE_VOTE_CAPABILITY_SHARE_ENCODED);

	String MESSAGE_VOTE_CAPABILITY_SHARE_ENCODED_BASE64 = Util
			.fromBigInt(MESSAGE_VOTE_CAPABILITY_SHARE_ENCODED);

}
