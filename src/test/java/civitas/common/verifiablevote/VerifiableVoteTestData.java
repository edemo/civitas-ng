package civitas.common.verifiablevote;

import java.util.Map;

import civitas.common.ConstructTestData;
import civitas.common.ballot.BallotTestData;
import civitas.crypto.proofvote.ProofVoteTestData;

public interface VerifiableVoteTestData
		extends ProofVoteTestData, BallotTestData {
	VerifiableVote VERIFIABLE_VOTE = new VerifiableVote(CONTEXT_0,
			EL_GAMAL_1_OF_L_REENCRYPTION, CIPHERTEXT_ENCCAP, PROOF_VOTE);

	String VERIFIABLE_VOTE_XML = "<verifiableVote><context>" + CONTEXT_0
			+ "</context><encChoice>" + EL_GAMAL_1_OF_L_REENCRYPTION_XML
			+ "</encChoice><encCapability>" + CIPHERTEXT_ENCCAP_XML
			+ "</encCapability><proof>" + PROOF_VOTE_XML
			+ "</proof></verifiableVote>";

	Map<Integer, VerifiableVote> VERIFIABLE_VOTE_MAP = ConstructTestData
			.constructTestData(VOTE_PIECES,
					(piece) -> new VerifiableVote(CONTEXT_MAP.get(piece),
							EL_GAMAL_1_OF_L_REENCRYPTION_MAP.get(BALLOT.matrix[piece]),
							ENCRYPTED_VOTE_CAPABILITIES.get(piece),
							PROOF_VOTE_MAP.get(piece)));
	VerifiableVote[] VERIFIABLE_VOTES = VERIFIABLE_VOTE_MAP.values()
			.toArray(new VerifiableVote[0]);

}
