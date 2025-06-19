package civitas.common.votersubmission;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import civitas.common.CommonConstants;
import civitas.common.ballotdesign.BallotDesign;
import civitas.common.ballotdesign.CalculatePositionInBallot;
import civitas.common.verifiablevote.VerifiableVote;
import civitas.common.verifiablevote.VerifyVerifiableVote;
import civitas.crypto.ciphertextlist.CiphertextList;
import civitas.crypto.publickey.ElGamalPublicKey;

@Service
public class CheckVoterSubmission implements CommonConstants {

	@Autowired
	CalculatePositionInBallot calculatePositionInBallot;
	@Autowired
	VerifyVerifiableVote verifyVerifiableVote;

	public final void apply(BallotDesign that, VoterSubmission vs,
			String baseContext, CiphertextList ciphertexts, ElGamalPublicKey pubKey)
			throws IllegalArgumentException {
		apply(that, vs, 0, baseContext, ciphertexts, pubKey);
	}

	public void apply(BallotDesign that, VoterSubmission vs, int startIndex,
			String context, CiphertextList ciphertexts, ElGamalPublicKey pubKey)
			throws IllegalArgumentException {
		if (pubKey == null) {
			throw new IllegalArgumentException("Invalid public Key");
		}
		if (context == null) {
			throw new IllegalArgumentException("Invalid context");
		}
		if (vs == null) {
			throw new IllegalArgumentException("Invalid voter submission.");
		}
		int k = that.candidates.length;
		for (int i = 0; i < k; i++) {
			for (int j = i + 1; j < k; j++) {
				VerifiableVote vv = vs.votes[startIndex
						+ calculatePositionInBallot.apply(i, j, k)];
				if (vv == null) {
					throw new IllegalArgumentException("Invalid verifiable vote.");
				}
				String vvcontext = vv.context;
				String desiredContext = context + KIND + i + ":" + j;
				if (!desiredContext.equals(vvcontext)) {
					throw new IllegalArgumentException(
							"Vote did not have correct context");
				}

				if (!verifyVerifiableVote.apply(vv, pubKey, ciphertexts,
						MAX_POSSIBLE_CHOICES)) {
					throw new IllegalArgumentException(
							"Vote choice odes not pass verification.");
				}
			}
		}
		return;
	}
}
