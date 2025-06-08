package civitas.crypto.proofvote;

import java.util.List;
import java.util.Map;

import civitas.common.ConstructTestData;
import civitas.common.Util;
import civitas.common.ballot.BallotTestData;
import civitas.crypto.ciphertext.ElGamalCiphertext;
import civitas.crypto.ciphertext.ElGamalEncrypt;
import civitas.crypto.oneoflreencryption.ElGamal1OfLReencryption;
import civitas.crypto.oneoflreencryption.ElGamal1OfLReencryptionTestData;
import civitas.crypto.votecapability.VoteCapability;
import civitas.util.CivitasBigInteger;
import civitas.util.DI;

public interface ProofVoteTestData
		extends ElGamal1OfLReencryptionTestData, BallotTestData {

	ElGamal1OfLReencryption ENCRYPTED_CHOICE = new ElGamal1OfLReencryption(
			REENCRYPTED_WELL_KNOWN_CHOICE, EL_GAMAL_PROOF_1_OF_L);

	public static final String PROOF_VOTE_C_BASE64 = "M1ePVeZ/yI560i1i05j4HF/m7bYqVrEwDGtFZcSjZUo=";
	public static final CivitasBigInteger PROOF_VOTE_C = Util
			.asBigint(PROOF_VOTE_C_BASE64);
	CivitasBigInteger PROOF_VOTE_S1 = RANDOMS_0.modSubtract(
			PROOF_VOTE_C.modMultiply(ELGAMAL_REENCRYPT_FACTOR_EPRIME.r, BIGINT_Q),
			BIGINT_Q);
	CivitasBigInteger PROOF_VOTE_S2 = RANDOMS_1.modSubtract(
			PROOF_VOTE_C.modMultiply(ELGAMAL_REENCRYPT_FACTOR_E.r, BIGINT_Q),
			BIGINT_Q);
	public static final ProofVote PROOF_VOTE = new ProofVote(PROOF_VOTE_C,
			PROOF_VOTE_S1, PROOF_VOTE_S2);

	public static final String PROOF_VOTE_S1_BASE64 = Util
			.fromBigInt(PROOF_VOTE_S1);
	public static final String PROOF_VOTE_S2_BASE64 = Util
			.fromBigInt(PROOF_VOTE_S2);
	public static final String PROOF_VOTE_XML = "<elGamalProofVote><c>"
			+ PROOF_VOTE_C_BASE64 + "</c><s1>" + PROOF_VOTE_S1_BASE64 + "</s1><s2>"
			+ PROOF_VOTE_S2_BASE64 + "</s2></elGamalProofVote>";
	public static final String PROOF_VOTE_NULL_XML = "<elGamalProofVote><c></c><s1></s1><s2></s2></elGamalProofVote>";

	List<VoteCapability> VOTE_CAPABILITIES = RANDOMS.subList(0, 3).stream()
			.map(x -> new VoteCapability(BIGINT_G.modPow(x, BIGINT_P))).toList();

	Map<String, VoteCapability> CAPABILITY_MAP = Map.of(CONTEXT_0,
			VOTE_CAPABILITIES.get(0), CONTEXT_1, VOTE_CAPABILITIES.get(1), CONTEXT_2,
			VOTE_CAPABILITIES.get(2));

	List<ElGamalCiphertext> ENCRYPTED_VOTE_CAPABILITIES = VOTE_CAPABILITIES
			.stream().map(x -> DI.get(ElGamalEncrypt.class)
					.apply(EL_GAMAL_PUBLIC_KEY_E, x, ELGAMAL_REENCRYPT_FACTOR_E))
			.toList();

	Map<Integer, ProofVote> PROOF_VOTE_MAP = ConstructTestData
			.constructTestData(VOTE_PIECES, (piece) -> {
				return DI.get(ConstructProofVote.class).apply(EL_GAMAL_PARAMETERS,
						ENCRYPTED_VOTE_CAPABILITIES.get(piece),
						REENCRYPTED_CHOICE_MAP.get(BALLOT.matrix[piece]),
						CONTEXT_MAP.get(piece), ELGAMAL_REENCRYPT_FACTOR_E,
						ELGAMAL_REENCRYPT_FACTOR_E);
			});

}
