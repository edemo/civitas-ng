package civitas.crypto.ciphertext.tests;

import civitas.common.VoteChoice;
import civitas.crypto.ciphertext.ElGamalCiphertext;
import civitas.crypto.msg.tests.ElgamalMsgTestData;
import civitas.crypto.publickey.tests.ElGamalPublicKeyTestData;
import civitas.crypto.reencryptfactor.tests.ElGamalReencryptFactorTestData;
import civitas.util.CivitasBigInteger;
import civitas.util.CivitasBigIntegerFactory;

public interface ElGamalCiphertextTestData
		extends ElgamalMsgTestData, ElGamalPublicKeyTestData, ElGamalReencryptFactorTestData {

	ElGamalCiphertext EL_GAMAL_CIPHERTEXT_ONE = new ElGamalCiphertext(ONE, ONE_ENCODED.m());
	ElGamalCiphertext EL_GAMAL_CIPHERTEXT_TWO = new ElGamalCiphertext(TWO, TWO_ENCODED.m());

	CivitasBigInteger CIPHERTEXT_E_A = BIGINT_G.modPow(FACTOR_E, BIGINT_P);
	CivitasBigInteger CIPHERTEXT_E_B =
			MESSAGE_VOTE_CAPABILITY_SHARE_ENCODED.modMultiply(PUBKEY_E.modPow(FACTOR_E, BIGINT_P), BIGINT_P);
	ElGamalCiphertext CIPHERTEXT_E = new ElGamalCiphertext(CIPHERTEXT_E_A, CIPHERTEXT_E_B);
	ElGamalCiphertext CIPHERTEXT_E_REENCRYPTED = new ElGamalCiphertext(
			CIPHERTEXT_E_A.modMultiply(BIGINT_G.modPow(RANDOMS_0, BIGINT_P), BIGINT_P),
			CIPHERTEXT_E_B.modMultiply(PUBKEY_EPRIME.modPow(RANDOMS_0, BIGINT_P), BIGINT_P));

	CivitasBigInteger CIPHERTEXT_ENCCAP_A = BIGINT_G.modPow(FACTOR_EPRIME, BIGINT_P);
	CivitasBigInteger CIPHERTEXT_ENCCAP_B =
			MESSAGE_VOTE_CAPABILITY_SHARE_ENCODED.modMultiply(PUBKEY_E.modPow(FACTOR_EPRIME, BIGINT_P), BIGINT_P);
	ElGamalCiphertext CIPHERTEXT_ENCCAP = new ElGamalCiphertext(CIPHERTEXT_ENCCAP_A, CIPHERTEXT_ENCCAP_B);

	CivitasBigInteger CIPHERTEXT_EPRIME_A = BIGINT_G.modPow(FACTOR_EPRIME, BIGINT_P);
	CivitasBigInteger CIPHERTEXT_EPRIME_B =
			MESSAGE_VOTE_CAPABILITY_SHARE_ENCODED.modMultiply(PUBKEY_E.modPow(FACTOR_EPRIME, BIGINT_P), BIGINT_P);
	ElGamalCiphertext CIPHERTEXT_EPRIME = new ElGamalCiphertext(CIPHERTEXT_EPRIME_A, CIPHERTEXT_EPRIME_B);

	ElGamalCiphertext EL_GAMAL_CIPHERTEXT = new ElGamalCiphertext(GENERATOR_OTHER_POW_R1, BIGINT_D);

	ElGamalCiphertext ENCRYPTED_WITH_FACTOR_EPRIME = new ElGamalCiphertext(
			G_EXP_FACTOR,
			MESSAGE_VOTE_CAPABILITY_SHARE_ENCODED.modMultiply(PUBKEY_EPRIME.modPow(FACTOR_EPRIME, BIGINT_P), BIGINT_P));

	ElGamalCiphertext ENCRYPTED_ZERO_FACTOR = new ElGamalCiphertext(ONE, G_EXP_B);

	byte[] VOTER_ADDITIONAL_ENV = "8+bob hash".getBytes();

	ElGamalCiphertext CIPHERTEXT_2_3 =
			new ElGamalCiphertext(CivitasBigIntegerFactory.obtain(2), CivitasBigIntegerFactory.obtain(3));
	ElGamalCiphertext[][] CIPHERTEXT_MATRIX = {
		{
			CIPHERTEXT_2_3,
			new ElGamalCiphertext(CivitasBigIntegerFactory.obtain(5), CivitasBigIntegerFactory.obtain(7)),
		},
		{
			new ElGamalCiphertext(CivitasBigIntegerFactory.obtain(11), CivitasBigIntegerFactory.obtain(13)),
			new ElGamalCiphertext(CivitasBigIntegerFactory.obtain(17), CivitasBigIntegerFactory.obtain(19)),
		}
	};
	VoteChoice MY_CHOICE = VoteChoice.I_BEATS_J;
}
