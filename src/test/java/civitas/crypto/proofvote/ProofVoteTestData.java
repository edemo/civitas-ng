package civitas.crypto.proofvote;

import static org.mockito.Mockito.mock;

import java.util.List;
import java.util.Map;

import civitas.common.ConstructTestData;
import civitas.common.ballot.BallotTestData;
import civitas.crypto.oneoflreencryption.ElGamal1OfLReencryption;
import civitas.crypto.oneoflreencryption.ElGamal1OfLReencryptionTestData;
import civitas.util.CivitasBigInteger;

public interface ProofVoteTestData
		extends ElGamal1OfLReencryptionTestData, BallotTestData {

	ElGamal1OfLReencryption ENCRYPTED_CHOICE = new ElGamal1OfLReencryption(
			REENCRYPTED_WELL_KNOWN_CHOICE, EL_GAMAL_PROOF_1_OF_L);

	List<CivitasBigInteger> PROOF_VOTE_ENVIRONMENT = List.of(
			EL_GAMAL_PARAMETERS.g, CIPHERTEXT_ENCCAP.a, CIPHERTEXT_ENCCAP.b,
			REENCRYPTED_WELL_KNOWN_CHOICE.a, REENCRYPTED_WELL_KNOWN_CHOICE.b,
			BIGINTEGER_HASH_OF_ADDITIONALENV, BIGINT_G.modPow(RANDOMS_0, BIGINT_P),
			BIGINT_G.modPow(RANDOMS_1, BIGINT_P));

	byte[] HASH_OF_PROOF_ENVIRONMENT = "hash of proof environment".getBytes();

	public static final CivitasBigInteger PROOF_VOTE_C = new CivitasBigInteger(1,
			HASH_OF_PROOF_ENVIRONMENT).mod(BIGINT_Q);
	CivitasBigInteger PROOF_VOTE_S1 = RANDOMS_0.modSubtract(
			PROOF_VOTE_C.modMultiply(ELGAMAL_REENCRYPT_FACTOR_EPRIME.r, BIGINT_Q),
			BIGINT_Q);
	CivitasBigInteger PROOF_VOTE_S2 = RANDOMS_1.modSubtract(
			PROOF_VOTE_C.modMultiply(ELGAMAL_REENCRYPT_FACTOR_E.r, BIGINT_Q),
			BIGINT_Q);
	public static final ProofVote PROOF_VOTE = new ProofVote(PROOF_VOTE_C,
			PROOF_VOTE_S1, PROOF_VOTE_S2);

	Map<Integer, ProofVote> PROOF_VOTE_MAP = ConstructTestData
			.constructTestData(VOTE_PIECES, (piece) -> mock(ProofVote.class,
					"PROOF_VOTE_" + VOTE_PIECES.indexOf(piece)));

}
