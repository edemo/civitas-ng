package civitas.common.votersubmission;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import civitas.common.CommonConstants;
import civitas.common.VoteChoice;
import civitas.common.ballotdesign.BallotDesign;
import civitas.common.ballotdesign.CalculatePositionInBallot;
import civitas.common.verifiablevote.VerifiableVote;
import civitas.common.verifiablevote.VerifyVerifiableVote;
import civitas.crypto.ciphertextlist.CiphertextList;
import civitas.crypto.publickey.ElGamalPublicKey;

@Controller
public class CheckVoterSubmission implements CommonConstants {

	@Autowired
	CalculatePositionInBallot calculatePositionInBallot;
	@Autowired
	VerifyVerifiableVote verifyVerifiableVote;

	public final void apply(final BallotDesign that, final VoterSubmission vs,
			final String baseContext, final CiphertextList ciphertexts,
			final ElGamalPublicKey pubKey) {
		apply(that, vs, 0, baseContext, ciphertexts, pubKey);
	}

	public void apply(final BallotDesign that, final VoterSubmission vs,
			final int startIndex, final String context,
			final CiphertextList ciphertexts, final ElGamalPublicKey pubKey) {
		if (pubKey == null) {
			throw new IllegalArgumentException("Invalid public Key");
		}
		if (context == null) {
			throw new IllegalArgumentException("Invalid context");
		}
		if (vs == null) {
			throw new IllegalArgumentException("Invalid voter submission.");
		}
		int k = that.getCandidates().length;
		for (int i = 0; i < k; i++) {
			for (int j = i + 1; j < k; j++) {
				VerifiableVote vv = vs.votes[startIndex
						+ calculatePositionInBallot.apply(i, j, k)];
				if (vv == null) {
					throw new IllegalArgumentException("Invalid verifiable vote.");
				}
				String vvcontext = vv.context();
				String desiredContext = context + KIND + i + ":" + j;
				if (!desiredContext.equals(vvcontext)) {
					throw new IllegalArgumentException(
							"Vote did not have correct context");
				}

				if (!verifyVerifiableVote.apply(vv, pubKey, ciphertexts,
						VoteChoice.values().length + 1)) {
					throw new IllegalArgumentException(
							"Vote choice odes not pass verification.");
				}
			}
		}
	}
}
