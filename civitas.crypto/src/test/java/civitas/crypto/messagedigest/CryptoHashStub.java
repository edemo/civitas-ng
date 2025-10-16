package civitas.crypto.messagedigest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.math.BigInteger;
import java.util.Base64;
import java.util.List;

import civitas.bboard.common.BBPostTestData;
import civitas.common.mix.capabilitymixrevelation.MixCapabilityElementRevelationTestData;
import civitas.common.tabteller.TabTellerKeyShareTestData;
import civitas.crypto.petcommitment.PETCommitmentTestData;
import civitas.crypto.proofdisclog.ElGamalProofDiscLogEqualityTestData;
import civitas.crypto.proofdvr.ElGamalProofDVRTestData;
import civitas.crypto.proofknowndisclog.ProofKnowDiscLogTestData;
import civitas.crypto.proofvote.ProofVoteTestData;
import civitas.crypto.signedciphertext.ElGamalSignedCiphertextTestData;
import civitas.util.CivitasBigInteger;

public class CryptoHashStub
		implements ElGamalProofDiscLogEqualityTestData,
				TabTellerKeyShareTestData,
				ElGamalProofDVRTestData,
				PETCommitmentTestData,
				ProofKnowDiscLogTestData,
				MixCapabilityElementRevelationTestData,
				ElGamalSignedCiphertextTestData,
				BBPostTestData,
				ProofVoteTestData {

	public static CryptoHash stub() {
		CryptoHash mock = mock(CryptoHash.class);
		@SuppressWarnings("unchecked")
		List<CivitasBigInteger> any = (List<CivitasBigInteger>) any();
		when(mock.apply(any)).thenReturn(SOMESTRING_HASH);
		when(mock.apply(ELECTORAL_ROLL_CAPABILITY_SHARES_XML.getBytes()))
				.thenReturn(ELECTORAL_ROLL_CAPABILITY_SHARES_XML_HASH);
		when(mock.apply(EL_GAMAL_DISC_LOG_EQUALITY_FOR_DECOMMITMENT_FOR_HASH))
				.thenReturn(Base64.getDecoder().decode(EL_GAMAL_DISC_LOG_EQUALITY_FOR_DECOMMITMENT_C_BASE64));
		when(mock.apply(ADDITIONALENV.getBytes())).thenReturn(HASH_OF_ADDITIONALENV);
		when(mock.apply(PROOF_VOTE_ENVIRONMENT)).thenReturn(HASH_OF_PROOF_ENVIRONMENT);
		when(mock.apply((TELLER_INDEX + VOTER_NAME).getBytes())).thenReturn(VOTER_ADDITIONAL_ENV);
		when(mock.apply(TAB_TELLER_KEY_SHARE_XML.getBytes())).thenReturn(TAB_TELLER_KEY_SHARE_HASH);
		when(mock.apply(
						G_POW_RANDOMS0_G_EXP_FACTOR_MESSAGE_MUL_PUBKEY_POW_FACTOR_ADDITIONALENV,
						CIPHERTEXT_E_A,
						CIPHERTEXT_E_B,
						ADDITIONALENV_BYTES))
				.thenReturn(HASH_OF_G_POW_RANDOMS0_G_EXP_FACTOR_MESSAGE_MUL_PUBKEY_POW_FACTOR_ADDITIONALENV);
		when(mock.apply(
						G_POW_RANDOMS0_G_EXP_FACTOR_MESSAGE_MUL_PUBKEY_POW_FACTOR_ADDITIONALENV,
						CIPHERTEXT_E_A,
						CIPHERTEXT_E_B,
						null))
				.thenReturn(HASH_OF_G_POW_RANDOMS0_G_EXP_FACTOR_MESSAGE_MUL_PUBKEY_POW_FACTOR);
		when(mock.apply(
						G_POW_RANDOMS0_G_EXP_FACTOR_MESSAGE_MUL_PUBKEY_POW_FACTOR_ADDITIONALENV_BAD,
						CIPHERTEXT_E_A,
						CIPHERTEXT_E_B,
						ADDITIONALENV_BYTES))
				.thenReturn(BIGINT_A);
		when(mock.apply(
						G_POW_RANDOMS0_G_EXP_FACTOR_MESSAGE_MUL_PUBKEY_POW_FACTOR_BAD,
						CIPHERTEXT_E_A,
						CIPHERTEXT_E_B,
						null))
				.thenReturn(BIGINT_A);
		when(mock.apply(EL_GAMAL_PROOF_1_OF_L_ENV))
				.thenReturn(Base64.getDecoder().decode(EL_GAMAL_PROOF_1_OF_L_HASH_BASE64));
		when(mock.apply(EL_GAMAL_PROOF_DVR_ENV)).thenReturn(EL_GAMAL_PROOF_DVR_HASH);
		when(mock.apply(FAKE_PROOF_DVR_ENV)).thenReturn(FAKE_PROOF_DVR_HASH);
		when(mock.apply(PET_COMMITMENT_DI, PET_COMMITMENT_EI, null, null)).thenReturn(PET_COMMITMENT_HASH);
		when(mock.apply(EL_GAMAL_PROOF_KNOWN_DISC_LOG_V, EL_GAMAL_PROOF_KNOWN_DISC_LOG_A, null, null))
				.thenReturn(EL_GAMAL_PROOF_KNOWN_DISC_LOG_C);
		when(mock.apply(
						CAPABILITY_ELEMENT_RELEVATION_LEFT_NONCE,
						BigInteger.valueOf(CAPABILITY_ELEMENT_RELEVATION_LEFT_MAPPING)
								.toByteArray()))
				.thenReturn(CAPABILITY_ELEMENT_RELEVATION_LEFT_HASH);
		when(mock.apply(
						CAPABILITY_ELEMENT_RELEVATION_RIGHT_NONCE,
						BigInteger.valueOf(CAPABILITY_ELEMENT_RELEVATION_RIGHT_MAPPING)
								.toByteArray()))
				.thenReturn(CAPABILITY_ELEMENT_RELEVATION_RIGHT_HASH);
		when(mock.apply(
						EL_GAMAL_SIGNED_CIPHERTEXT_HASH1,
						EL_GAMAL_SIGNED_CIPHERTEXT_A,
						EL_GAMAL_SIGNED_CIPHERTEXT_B,
						ADDITIONALENV_BYTES))
				.thenReturn(EL_GAMAL_SIGNED_CIPHERTEXT_C);
		when(mock.apply(BULLETIN_BOARD_ID.getBytes(), BLOCK0_META.getBytes())).thenReturn(BLOCK0_HASH);
		when(mock.apply(BULLETIN_BOARD_ID.getBytes(), BLOCK1_META.getBytes())).thenReturn(BLOCK1_HASH);
		when(mock.apply(
						BBPOST.hash,
						BigInteger.valueOf(CURRENT_TIME).toByteArray(),
						BOARD_CLOSED_CONTENT_COMMITMENT_SIGNATURE.signatureBytes))
				.thenReturn(BOARD_CLOSED__CONTENT_COMMITMENT_SIGNATURE_HASH);
		when(mock.apply(
						new byte[0],
						BigInteger.valueOf(CURRENT_TIME).toByteArray(),
						BOARD_CLOSED_CONTENT_COMMITMENT_SIGNATURE.signatureBytes))
				.thenReturn(BOARD_CLOSED__CONTENT_COMMITMENT_SIGNATURE_HASH_NOPREV);
		when(mock.apply(SOMESTRING.getBytes())).thenReturn(SOMESTRING_HASH);
		return mock;
	}
}
