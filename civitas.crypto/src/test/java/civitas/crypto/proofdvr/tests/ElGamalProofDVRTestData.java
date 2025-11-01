package civitas.crypto.proofdvr.tests;

import static org.mockito.Mockito.mock;

import java.util.List;

import civitas.crypto.ciphertextlist.tests.ElGamalCiphertextListTestData;
import civitas.crypto.proofdvr.ElGamalProofDVR;
import civitas.util.CivitasBigInteger;
import civitas.util.CivitasBigIntegerFactory;

public interface ElGamalProofDVRTestData extends ElGamalCiphertextListTestData {

	byte[] EL_GAMAL_PROOF_DVR_HASH = "proofdvrhash".getBytes();
	CivitasBigInteger DVR_HASH = CivitasBigIntegerFactory.obtain(1, EL_GAMAL_PROOF_DVR_HASH);

	CivitasBigInteger DVR_U =
			RANDOMS_0.modAdd(ZETA.modMultiply(DVR_HASH.modAdd(RANDOMS_1, BIGINT_Q), BIGINT_Q), BIGINT_Q);
	ElGamalProofDVR EL_GAMAL_PROOF_DVR =
			new ElGamalProofDVR(CIPHERTEXT_E, CIPHERTEXT_EPRIME, DVR_HASH, RANDOMS_1, RANDOMS_2, DVR_U);
	ElGamalProofDVR EL_GAMAL_PROOF_DVR_BAD =
			new ElGamalProofDVR(CIPHERTEXT_E, CIPHERTEXT_EPRIME, BIGINT_B, RANDOMS_1, RANDOMS_2, DVR_U);

	CivitasBigInteger EL_GAMAL_PROOF_DVR_AP = BIGINT_G.modPow(EL_GAMAL_PROOF_DVR.u(), BIGINT_P)
			.modDivide(
					EL_GAMAL_PROOF_DVR
							.eprime()
							.getA()
							.modDivide(EL_GAMAL_PROOF_DVR.e().getA(), BIGINT_P)
							.modPow(EL_GAMAL_PROOF_DVR.c().modAdd(EL_GAMAL_PROOF_DVR.w(), BIGINT_Q), BIGINT_P),
					BIGINT_P);
	CivitasBigInteger EL_GAMAL_PROOF_DVR_BP = EL_GAMAL_PUBLIC_KEY_E
			.y
			.modPow(EL_GAMAL_PROOF_DVR.u(), BIGINT_P)
			.modDivide(
					EL_GAMAL_PROOF_DVR
							.eprime()
							.b
							.modDivide(EL_GAMAL_PROOF_DVR.e().getB(), BIGINT_P)
							.modPow(EL_GAMAL_PROOF_DVR.c().modAdd(EL_GAMAL_PROOF_DVR.w(), BIGINT_Q), BIGINT_P),
					BIGINT_P);
	CivitasBigInteger EL_GAMAL_PROOF_DVR_SP = BIGINT_G.modPow(EL_GAMAL_PROOF_DVR.w(), BIGINT_P)
			.modMultiply(EL_GAMAL_PUBLIC_KEY_EPRIME.y.modPow(EL_GAMAL_PROOF_DVR.r(), BIGINT_P), BIGINT_P);

	List<CivitasBigInteger> EL_GAMAL_PROOF_DVR_ENV = List.of(
			EL_GAMAL_PROOF_DVR.e().getA(),
			EL_GAMAL_PROOF_DVR.e().getB(),
			EL_GAMAL_PROOF_DVR.eprime().a,
			EL_GAMAL_PROOF_DVR.eprime().b,
			EL_GAMAL_PROOF_DVR_AP,
			EL_GAMAL_PROOF_DVR_BP,
			EL_GAMAL_PROOF_DVR_SP);

	CivitasBigInteger FAKE_PROOF_DVR_AT = BIGINT_G.modPow(RANDOMS_2, BIGINT_P)
			.modDivide(CIPHERTEXT_EPRIME.a.modDivide(CIPHERTEXT_E.a, BIGINT_P).modPow(RANDOMS_0, BIGINT_P), BIGINT_P);
	CivitasBigInteger FAKE_PROOF_DVR_BT = EL_GAMAL_PUBLIC_KEY_E
			.y
			.modPow(RANDOMS_2, BIGINT_P)
			.modDivide(CIPHERTEXT_EPRIME.b.modDivide(CIPHERTEXT_E.b, BIGINT_P).modPow(RANDOMS_0, BIGINT_P), BIGINT_P);
	CivitasBigInteger FAKE_PROOF_DVR_ST = BIGINT_G.modPow(RANDOMS_1, BIGINT_P);

	List<CivitasBigInteger> FAKE_PROOF_DVR_ENV = List.of(
			CIPHERTEXT_E.getA(),
			CIPHERTEXT_E.getB(),
			CIPHERTEXT_EPRIME.getA(),
			CIPHERTEXT_EPRIME.getB(),
			FAKE_PROOF_DVR_AT,
			FAKE_PROOF_DVR_BT,
			FAKE_PROOF_DVR_ST);
	byte[] FAKE_PROOF_DVR_HASH = "fakeproofdvrhash".getBytes();
	CivitasBigInteger FAKE_PROOF_DVR_CT =
			CivitasBigIntegerFactory.obtain(1, FAKE_PROOF_DVR_HASH).mod(BIGINT_Q);

	CivitasBigInteger FAKE_PROOF_DVR_WT = RANDOMS_0.modSubtract(FAKE_PROOF_DVR_CT, BIGINT_Q);
	CivitasBigInteger FAKE_PROOF_DVR_RT =
			RANDOMS_1.modSubtract(FAKE_PROOF_DVR_WT, BIGINT_Q).modDivide(EL_GAMAL_PRIVATE_KEY_EPRIME.x(), BIGINT_Q);

	ElGamalProofDVR FAKE_PROOF_DVR = new ElGamalProofDVR(
			CIPHERTEXT_E, CIPHERTEXT_EPRIME, FAKE_PROOF_DVR_CT, FAKE_PROOF_DVR_WT, FAKE_PROOF_DVR_RT, RANDOMS_2);

	List<ElGamalProofDVR> PROOF_LIST = ENCRYPTED_SIGNED_VOTE_CAPABILITIES.stream()
			.map(x -> new ElGamalProofDVR(
					x,
					PROOF_EPRIMES.get(ENCRYPTED_SIGNED_VOTE_CAPABILITIES.indexOf(x)),
					mock(CivitasBigInteger.class),
					mock(CivitasBigInteger.class),
					mock(CivitasBigInteger.class),
					mock(CivitasBigInteger.class)))
			.toList();
	ElGamalProofDVR[] PROOFS = PROOF_LIST.toArray(new ElGamalProofDVR[0]);

	ElGamalProofDVR[] PROOFS_CAP_NONVERIFY = PROOF_LIST.stream()
			.map(x -> new ElGamalProofDVR(
					POSTED_CAPABILITIES_NONVERIFY[PROOF_LIST.indexOf(x)], x.eprime(), x.c(), x.w(), x.r(), x.u()))
			.toList()
			.toArray(new ElGamalProofDVR[0]);
}
