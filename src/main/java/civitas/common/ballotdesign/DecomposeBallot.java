package civitas.common.ballotdesign;

import java.util.Map;

import civitas.common.CommonConstants;
import civitas.common.ballot.Ballot;
import civitas.common.verifiablevote.VerifiableVote;
import civitas.common.votersubmission.CreateVoterSubmission;
import civitas.common.votersubmission.VoterSubmission;
import civitas.crypto.ciphertext.ElGamalCiphertext;
import civitas.crypto.ciphertext.ElGamalEncrypt;
import civitas.crypto.ciphertextlist.CiphertextList;
import civitas.crypto.oneoflreencryption.ConstructElGamal1OfLReencryption;
import civitas.crypto.oneoflreencryption.ElGamal1OfLReencryption;
import civitas.crypto.proofvote.ConstructProofVote;
import civitas.crypto.proofvote.ProofVote;
import civitas.crypto.publickey.ElGamalPublicKey;
import civitas.crypto.reencryptfactor.ElGamalReencryptFactor;
import civitas.crypto.reencryptfactor.GenerateElGamalReencryptFactor;
import civitas.crypto.votecapability.VoteCapability;
import civitas.util.Use;

public class DecomposeBallot implements CommonConstants {

	@Use
	CalculatePositionInBallot calculatePositionInBallot;
	@Use
	CalculateBallotLength calculateBallotLength;
	@Use
	ConstructElGamal1OfLReencryption constructElGamal1OfLReencryption;
	@Use
	GenerateElGamalReencryptFactor generateElGamalReencryptFactor;
	@Use
	ElGamalEncrypt elGamalEncrypt;
	@Use
	ConstructProofVote constructProofVote;
	@Use
	CreateVoterSubmission createVoterSubmission;

	public VoterSubmission apply(BallotDesign that, Ballot ballot, int voterBlock,
			ElGamalPublicKey key, CiphertextList ciphertexts, String context,
			Map<String, VoteCapability> capabilities)
			throws IllegalArgumentException {

		if (!(ballot instanceof Ballot)) {
			throw new IllegalArgumentException("Incorrect kind of ballot.");
		}
		if (key == null) {
			throw new IllegalArgumentException("No key supplied");
		}
		Ballot cb = ballot;
		int[] cbMatrix = cb.matrix;
		if (cbMatrix == null || that.candidates == null) {
			throw new IllegalArgumentException("Missing slate.");
		}
		if (cb.k != that.candidates.length
				|| cbMatrix.length != calculateBallotLength.apply(cb.k)) {
			throw new IllegalArgumentException(
					"The ballot's matrix size is not correct.");
		}

		// add one vote for each matrix entry.
		// FIXME call once
		int matrixSize = calculateBallotLength.apply(cb.k);
		VerifiableVote[] votes = new VerifiableVote[matrixSize];
		for (int i = 0; i < cb.k; i++) {
			for (int j = i + 1; j < cb.k; j++) {
				ElGamalReencryptFactor encChoiceFactor = null;
				ElGamal1OfLReencryption encChoice = null;
				int pos = calculatePositionInBallot.apply(i, j, cb.k);
				int choice = cbMatrix[calculatePositionInBallot.apply(i, j, cb.k)];// FIXME
																																						// use
																																						// pos
				encChoiceFactor = generateElGamalReencryptFactor.apply(key.params);
				encChoice = constructElGamal1OfLReencryption.apply(key, ciphertexts, 4,
						choice, encChoiceFactor);

				String desiredContext = context + KIND + i + ":" + j;
				VoteCapability c = null;
				try {
					c = capabilities.get(desiredContext);
				} catch (NullPointerException e) {
					throw new IllegalArgumentException(
							"Not enough capabilities supplied");
				} catch (ClassCastException e) {
					throw new IllegalArgumentException(
							"Not enough capabilities supplied");
				}
				if (c == null) {
					throw new IllegalArgumentException(
							"No capability supplied for context " + desiredContext);
				}

				ElGamalReencryptFactor encCapFactor = generateElGamalReencryptFactor
						.apply(key.params);
				ElGamalCiphertext encCap = elGamalEncrypt.apply(key, c, encCapFactor);
				ProofVote proofVote = constructProofVote.apply(key.params, encCap,
						encChoice.m, desiredContext, encCapFactor, encChoiceFactor);

				VerifiableVote v = new VerifiableVote(desiredContext, encChoice, encCap,
						proofVote);

				try {
					votes[pos] = v;
				} catch (ArrayIndexOutOfBoundsException imposs) {
					throw new IllegalArgumentException("Internal error");
				}
			}
		}
		VoterSubmission vs = createVoterSubmission.apply(voterBlock, votes);
		return vs;
	}

}
