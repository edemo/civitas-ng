package civitas.common.ballotdesign;

import java.util.Map;

import civitas.common.CommonConstants;
import civitas.common.ballot.Ballot;
import civitas.common.verifiablevote.VerifiableVote;
import civitas.common.votersubmission.VoterSubmission;
import civitas.crypto.CryptoUtil;
import civitas.crypto.ciphertext.ElGamalCiphertext;
import civitas.crypto.ciphertextlist.CiphertextList;
import civitas.crypto.oneoflreencryption.ElGamal1OfLReencryption;
import civitas.crypto.proofvote.ProofVote;
import civitas.crypto.publickey.ElGamalPublicKey;
import civitas.crypto.reencryptfactor.ElGamalReencryptFactor;
import civitas.crypto.votecapability.VoteCapability;
import civitas.util.Use;

public class DecomposeBallot implements CommonConstants {

	@Use
	CalculatePositionInBallot calculatePositionInBallot;
	@Use
	CalculateBallotLength calculateBallotLength;

	public VoterSubmission decompose(BallotDesign that, Ballot ballot,
			int voterBlock, ElGamalPublicKey key, CiphertextList ciphertexts,
			String context, Map<String, VoteCapability> capabilities)
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
		VerifiableVote[] votes = new VerifiableVote[matrixSize < 0 ? 0
				: matrixSize];
		for (int i = 0; i < cb.k; i++) {
			for (int j = i + 1; j < cb.k; j++) {
				ElGamalReencryptFactor encChoiceFactor = null;
				ElGamal1OfLReencryption encChoice = null;
				int pos = calculatePositionInBallot.apply(i, j, cb.k);
				try {
					int choice = cbMatrix[calculatePositionInBallot.apply(i, j, cb.k)];// FIXME
																																							// use
																																							// pos
					encChoiceFactor = CryptoUtil.factory()
							.generateElGamalReencryptFactor(key.params);
					encChoice = CryptoUtil.factory().elGamal1OfLReencrypt(key,
							ciphertexts, 4, choice, encChoiceFactor);
				} catch (NullPointerException imposs) {
				} catch (ArrayIndexOutOfBoundsException imposs) {
				}

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

				ElGamalReencryptFactor encCapFactor = null;
				ElGamalCiphertext encCap = null;
				ProofVote proofVote = null;
				try {
					encCapFactor = CryptoUtil.factory()
							.generateElGamalReencryptFactor(key.params);
					encCap = CryptoUtil.factory().elGamalEncrypt(key, c, encCapFactor);
					proofVote = CryptoUtil.factory().constructProofVote(key.params,
							encCap, encChoice, desiredContext, encCapFactor, encChoiceFactor);
				} catch (NullPointerException imposs) {
				}

				VerifiableVote v = new VerifiableVote(desiredContext, encChoice, encCap,
						proofVote);

				try {
					votes[pos] = v;
				} catch (ArrayIndexOutOfBoundsException imposs) {
					throw new IllegalArgumentException("Internal error");
				}
			}
		}
		VoterSubmission vs = new VoterSubmission(voterBlock, votes);
		return vs;
	}

}
