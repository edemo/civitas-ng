package civitas.crypto.petcommitment;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import civitas.common.TestBase;
import civitas.crypto.algorithms.CryptoHash;
import civitas.crypto.ciphertext.ElGamalCiphertext;
import civitas.crypto.petshare.PETShareTestData;
import civitas.util.CivitasBigInteger;
import civitas.util.Tested;
import civitas.util.Use;

public class ConstructPETCommitmentTest extends TestBase
		implements PETShareTestData {
	@Tested
	ConstructPETCommitment constructPETCommitment;
	@Use
	CryptoHash hash;

	@Test
	@DisplayName("commitment returns hash((c1.a/c2.a)^exponent, (c1.b/c2.b)^exponent) (mod p)")
	void test6() {
		ElGamalCiphertext c1 = PET_SHARE.ciphertext1;
		ElGamalCiphertext c2 = PET_SHARE.ciphertext2;
		CivitasBigInteger exponent = PET_SHARE.exponent;

		CivitasBigInteger hashe = hash.apply(
				c1.a.modDivide(c2.a, BIGINT_P).modPow(exponent, BIGINT_P),
				c1.b.modDivide(c2.b, BIGINT_P).modPow(exponent, BIGINT_P));

		assertEquals(hashe,
				constructPETCommitment.apply(PET_SHARE, EL_GAMAL_PARAMETERS).hash);
	}

}
