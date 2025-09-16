package civitas.common.ballotdesign;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import civitas.common.CommonConstants;
import civitas.common.VoteChoice;
import civitas.common.ballot.Ballot;
import civitas.common.capabilityencryption.CapabilityEncryption;
import civitas.common.capabilityencryption.EncryptCapability;
import civitas.common.encryptedchoice.EncryptChoice;
import civitas.common.encryptedchoice.EncryptedChoice;
import civitas.common.verifiablevote.VerifiableVote;
import civitas.common.votersubmission.CreateVoterSubmission;
import civitas.common.votersubmission.VoterSubmission;
import civitas.crypto.ciphertextlist.CiphertextList;
import civitas.crypto.proofvote.ConstructProofVote;
import civitas.crypto.proofvote.ProofVote;
import civitas.crypto.publickey.ElGamalPublicKey;
import civitas.crypto.votecapability.VoteCapability;

@Controller
public class DecomposeBallot implements CommonConstants {

	@Autowired
	EncryptCapability encryptCapability;
	@Autowired
	EncryptChoice encryptChoice;

	@Autowired
	CalculatePositionInBallot calculatePositionInBallot;
	@Autowired
	CalculateBallotLength calculateBallotLength;
	@Autowired
	ConstructProofVote constructProofVote;
	@Autowired
	CreateVoterSubmission createVoterSubmission;

	public VoterSubmission apply(final BallotDesign that, final Ballot ballot,
			final int voterBlock, final ElGamalPublicKey key,
			final CiphertextList ciphertexts, final String context,
			final Map<String, VoteCapability> capabilities) {

		if (key == null) {
			throw new IllegalArgumentException("No key supplied");
		}
		VoteChoice[] cbMatrix = ballot.matrix;
		int matrixSize = calculateBallotLength.apply(ballot.k);
		if (ballot.k != that.candidates.length || cbMatrix.length != matrixSize) {
			throw new IllegalArgumentException(
					"The ballot's matrix size is not correct.");
		}

		VerifiableVote[] votes = new VerifiableVote[matrixSize];
		for (int i = 0; i < ballot.k; i++) {
			for (int j = i + 1; j < ballot.k; j++) {
				int pos = calculatePositionInBallot.apply(i, j, ballot.k);

				EncryptedChoice encryptedChoice = encryptChoice.apply(key, ciphertexts,
						cbMatrix, pos);

				String desiredContext = context + KIND + i + ":" + j;
				CapabilityEncryption encryptedCapability = encryptCapability.apply(key,
						capabilities, desiredContext);
				ProofVote proofVote = constructProofVote.apply(key.params(),
						encryptedCapability.encCap(), encryptedChoice.encChoice().m(),
						desiredContext, encryptedCapability.factor(), encryptedChoice.factor());

				VerifiableVote v = new VerifiableVote(desiredContext,
						encryptedChoice.encChoice(), encryptedCapability.encCap(), proofVote);

				votes[pos] = v;
			}
		}
		return createVoterSubmission.apply(voterBlock, votes);
	}

}
