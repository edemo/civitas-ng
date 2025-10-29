package civitas.crypto.decriptionshare.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import civitas.crypto.ciphertext.tests.ElGamalCiphertextTestData;
import civitas.crypto.decriptionshare.CombineDecryptionShares;
import civitas.crypto.msg.ElGamalMsg;
import civitas.util.CivitasBigInteger;
import io.github.magwas.konveyor.testing.TestBase;

class CombineDecryptionSharesTest extends TestBase
		implements ElGamalDecryptionShareTestData, ElGamalCiphertextTestData {

	@InjectMocks
	CombineDecryptionShares combineDecryptionShares;

	@Test
	@DisplayName(
			"""
			Creates a message containing the combination of the ciphertext and the shares
				prod = prod(ai[i] i= 1..n) (mod p)
				m = c.b/prod (mod p)
			""")
	void test() {

		CivitasBigInteger m = CIPHERTEXT_E.b.modDivide(
				EL_GAMAL_DECRYPTION_SHARE_AI.modMultiply(EL_GAMAL_DECRYPTION_SHARE_AI, BIGINT_P), BIGINT_P);

		ElGamalMsg result =
				combineDecryptionShares.apply(CIPHERTEXT_E, EL_GAMAL_DECRYPTION_SHARES, EL_GAMAL_PARAMETERS);

		assertEquals(m, result.m());
	}
}
