package civitas.crypto.algorithms;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import civitas.crypto.concrete.ConcreteTestBase;
import civitas.crypto.concrete.ElGamalCiphertextCTestData;
import civitas.crypto.concrete.ElGamalDecryptionShareC;
import civitas.crypto.concrete.ElGamalDecryptionShareTestData;
import civitas.crypto.concrete.ElGamalMsgC;
import civitas.util.CivitasBigInteger;
import civitas.util.Tested;

public class CombineDecryptionSharesTest extends ConcreteTestBase
		implements ElGamalDecryptionShareTestData, ElGamalCiphertextCTestData {

	@Tested
	CombineDecryptionShares combineDecryptionShares;

	@Test
	@DisplayName("Creates a message containing the combination of the ciphertext and the shares"
			+ "		prod = prod(ai[i] i= 1..n) (mod p)\n" + "		m = c.b/prod (mod p)\n")
	void test() {

		ElGamalDecryptionShareC[] shares = new ElGamalDecryptionShareC[] {
				EL_GAMAL_DECRYPTION_SHARE,
				EL_GAMAL_DECRYPTION_SHARE_NULLPROOF };

		CivitasBigInteger m = CIPHERTEXT_E.b.modDivide(
				EL_GAMAL_DECRYPTION_SHARE.ai.modMultiply(RANDOMS_1, BIGINT_P),
				BIGINT_P);

		ElGamalMsgC result = (ElGamalMsgC) combineDecryptionShares
				.apply(CIPHERTEXT_E, shares, EL_GAMAL_PARAMETERS);
		assertEquals(m, result.m);
	}
}
