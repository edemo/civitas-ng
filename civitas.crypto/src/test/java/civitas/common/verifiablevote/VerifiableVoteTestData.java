package civitas.common.verifiablevote;

import java.util.Map;

import civitas.common.ConstructTestData;
import civitas.common.ballot.BallotTestData;
import civitas.crypto.oneoflreencryption.ElGamal1OfLReencryption;
import civitas.crypto.oneoflreencryption.ElGamal1OfLReencryptionTestData;
import civitas.crypto.proofvote.ProofVoteTestData;

public interface VerifiableVoteTestData extends ProofVoteTestData, BallotTestData, ElGamal1OfLReencryptionTestData {
	VerifiableVote VERIFIABLE_VOTE =
			new VerifiableVote(CONTEXT_0, EL_GAMAL_1_OF_L_REENCRYPTION, CIPHERTEXT_ENCCAP, PROOF_VOTE);

	VerifiableVote VERIFIABLE_VOTE_BAD_CHOICE = new VerifiableVote(
			CONTEXT_0,
			new ElGamal1OfLReencryption(REENCRYPTED_WELL_KNOWN_CHOICE, EL_GAMAL_PROOF_1_OF_L_BAD),
			CIPHERTEXT_ENCCAP,
			PROOF_VOTE);

	VerifiableVote VERIFIABLE_VOTE_BAD_PROOF =
			new VerifiableVote(CONTEXT_0, EL_GAMAL_1_OF_L_REENCRYPTION, CIPHERTEXT_ENCCAP, PROOF_VOTE_MAP.get(1));

	Map<Integer, VerifiableVote> VERIFIABLE_VOTE_MAP = ConstructTestData.constructTestData(
			VOTE_PIECES,
			piece -> new VerifiableVote(
					CONTEXT_MAP.get(piece),
					EL_GAMAL_1_OF_L_REENCRYPTION_MAP.get(BALLOT.matrix[piece]),
					ENCRYPTED_SIGNED_VOTE_CAPABILITIES.get(piece),
					PROOF_VOTE_MAP.get(piece)));
	VerifiableVote[] VERIFIABLE_VOTES = VERIFIABLE_VOTE_MAP.values().toArray(new VerifiableVote[0]);
}
