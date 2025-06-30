package civitas.crypto.msg;

import civitas.crypto.publickey.ElGamalPublicKeyTestData;
import civitas.util.CivitasBigInteger;

public interface ElgamalMessageTestData extends ElGamalPublicKeyTestData {

	public static final String VOTE = "3,(2,5),0,4,1";

	CivitasBigInteger MESSAGE_VOTE_CAPABILITY_SHARE = new CivitasBigInteger(
			VOTE.getBytes());

	CivitasBigInteger MESSAGE_VOTE_CAPABILITY_SHARE_ENCODED = BIGINT_G
			.modPow(MESSAGE_VOTE_CAPABILITY_SHARE, BIGINT_P);
	ElGamalMsg EL_GAMAL_MESSAGE_VOTE_CAPABILITY_SHARE = new ElGamalMsg(
			MESSAGE_VOTE_CAPABILITY_SHARE_ENCODED);

}
