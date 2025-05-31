package civitas.crypto.decriptionshare;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import civitas.common.TestBase;
import civitas.crypto.ciphertext.ElGamalCiphertextCTestData;
import civitas.crypto.msg.ElGamalMsg;
import civitas.util.CivitasBigInteger;
import civitas.util.Tested;

public class CombineDecryptionSharesTest extends TestBase
		implements ElGamalDecryptionShareTestData, ElGamalCiphertextCTestData {

	@Tested
	CombineDecryptionShares combineDecryptionShares;

	@Test
	@DisplayName("Creates a message containing the combination of the ciphertext and the shares"
			+ "		prod = prod(ai[i] i= 1..n) (mod p)\n" + "		m = c.b/prod (mod p)\n")
	void test() {

		ElGamalDecryptionShare[] shares = new ElGamalDecryptionShare[] {
				EL_GAMAL_DECRYPTION_SHARE,
				EL_GAMAL_DECRYPTION_SHARE_BADPROOF };

		CivitasBigInteger m = CIPHERTEXT_E.b.modDivide(
				EL_GAMAL_DECRYPTION_SHARE.ai.modMultiply(RANDOMS_1, BIGINT_P),
				BIGINT_P);

		ElGamalMsg result = combineDecryptionShares.apply(CIPHERTEXT_E, shares,
				EL_GAMAL_PARAMETERS);
		assertEquals(m, result.m);
	}
}
