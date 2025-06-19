package civitas.crypto.petcommitment;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import civitas.AppTestConfig;
import civitas.common.TestBase;
import civitas.crypto.ciphertext.ElGamalCiphertextish;
import civitas.crypto.messagedigest.CryptoHash;
import civitas.crypto.petshare.PETShareTestData;
import civitas.util.CivitasBigInteger;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = AppTestConfig.class)
public class ConstructPETCommitmentTest extends TestBase
		implements PETShareTestData {
	@InjectMocks
	ConstructPETCommitment constructPETCommitment;
	@Autowired
	CryptoHash hash;

	@Test
	@DisplayName("commitment returns hash((c1.a/c2.a)^exponent, (c1.b/c2.b)^exponent) (mod p)")
	void test6() {
		ElGamalCiphertextish c1 = PET_SHARE.ciphertext1;
		ElGamalCiphertextish c2 = PET_SHARE.ciphertext2;
		CivitasBigInteger exponent = PET_SHARE.exponent;

		CivitasBigInteger hashe = hash.apply(
				c1.getA().modDivide(c2.getA(), BIGINT_P).modPow(exponent, BIGINT_P),
				c1.getB().modDivide(c2.getB(), BIGINT_P).modPow(exponent, BIGINT_P));

		assertEquals(hashe,
				constructPETCommitment.apply(PET_SHARE, EL_GAMAL_PARAMETERS).hash);
	}

}
