package civitas.crypto.msg;

import civitas.crypto.publickey.ElGamalPublicKeyTestData;
import civitas.util.CivitasBigInteger;

public interface ElgamalMsgTestData extends ElGamalPublicKeyTestData {

	public static final String VOTE = "3,(2,5),0,4,1";

	ElGamalMsg ONE_ENCODED = new ElGamalMsg(BIGINT_G.modPow(ONE, BIGINT_P));
	ElGamalMsg TWO_ENCODED = new ElGamalMsg(BIGINT_G.modPow(TWO, BIGINT_P));

	CivitasBigInteger MESSAGE_VOTE_CAPABILITY_SHARE = new CivitasBigInteger(
			VOTE.getBytes());

	CivitasBigInteger MESSAGE_VOTE_CAPABILITY_SHARE_ENCODED = BIGINT_G
			.modPow(MESSAGE_VOTE_CAPABILITY_SHARE, BIGINT_P);
	ElGamalMsg EL_GAMAL_MESSAGE_VOTE_CAPABILITY_SHARE = new ElGamalMsg(
			MESSAGE_VOTE_CAPABILITY_SHARE_ENCODED);

}
