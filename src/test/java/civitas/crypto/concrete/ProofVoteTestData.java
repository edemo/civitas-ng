package civitas.crypto.concrete;

import civitas.common.Util;
import civitas.util.CivitasBigInteger;

public interface ProofVoteTestData {
	public static final String PROOF_VOTE_C_BASE64 = "JLjaqZUzF9Ja/0klrpAtIPe2sETCRf+0OlWeiI1TV/M=";
	public static final String PROOF_VOTE_S1_BASE64 = "S/9gPijKaeaDapS+YtHnBzKupvsiFDWRIaO5r1t1Ja8=";
	public static final String PROOF_VOTE_S2_BASE64 = "RpmdQslm/BT1fr9Bf2NsCdPMiY+ch4reYv+bEaa/RMo=";
	public static final String PROOF_VOTE_XML = "<elGamalProofVote><c>"
			+ PROOF_VOTE_C_BASE64 + "</c><s1>" + PROOF_VOTE_S1_BASE64 + "</s1><s2>"
			+ PROOF_VOTE_S2_BASE64 + "</s2></elGamalProofVote>";
	public static final String PROOF_VOTE_NULL_XML = "<elGamalProofVote><c></c><s1></s1><s2></s2></elGamalProofVote>";

	public static final CivitasBigInteger PROOF_VOTE_C = Util
			.asBigint(PROOF_VOTE_C_BASE64);
	public static final CivitasBigInteger PROOF_VOTE_S1 = Util
			.asBigint(PROOF_VOTE_S1_BASE64);
	public static final CivitasBigInteger PROOF_VOTE_S2 = Util
			.asBigint(PROOF_VOTE_S2_BASE64);

	public static final ProofVoteC PROOF_VOTE = new ProofVoteC(PROOF_VOTE_C,
			PROOF_VOTE_S1, PROOF_VOTE_S2);

}
