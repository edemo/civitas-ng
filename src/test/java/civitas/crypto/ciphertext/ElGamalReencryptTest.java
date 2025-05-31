package civitas.crypto.ciphertext;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import civitas.crypto.ConcreteTestBase;
import civitas.crypto.publickey.ElGamalPublicKeyCTestData;
import civitas.crypto.reencryptfactor.ElGamalReencryptFactor;
import civitas.util.CivitasBigInteger;
import civitas.util.Tested;

public class ElGamalReencryptTest extends ConcreteTestBase
		implements ElGamalPublicKeyCTestData {

	@Tested
	ElGamalReencrypt elGamalReencrypt;

	@Test
	@DisplayName("elGamalReencrypt works as expected: "
			+ "c1:=c1*g^y, c2:=c2*m^y, where y is random, all mod p")
	void test1() throws Exception {

		CivitasBigInteger p = EL_GAMAL_PARAMETERS.p;
		CivitasBigInteger g = EL_GAMAL_PARAMETERS.g;
		CivitasBigInteger y = RANDOMS_0;
		CivitasBigInteger m = EL_GAMAL_PUBLIC_KEY_EPRIME.y;
		CivitasBigInteger c1 = BIGINT_A;
		CivitasBigInteger c2 = BIGINT_B;

		ElGamalCiphertext cipherText = new ElGamalCiphertext(c1, c2);

		c1 = c1.modMultiply(g.modPow(y, p), p);
		c2 = c2.modMultiply(m.modPow(y, p), p);

		assertEquals(new ElGamalCiphertext(c1, c2),
				elGamalReencrypt.apply(EL_GAMAL_PUBLIC_KEY_EPRIME, cipherText));

	}

	@Test
	@DisplayName("elGamalReencrypt with a factor works as expected: "
			+ "c1:=c1*g^y, c2:=c2*m^y, where y is random, all mod p")
	void test1_1() throws Exception {

		CivitasBigInteger y = RANDOMS_0;
		CivitasBigInteger p = EL_GAMAL_PARAMETERS.p;
		CivitasBigInteger g = EL_GAMAL_PARAMETERS.g;
		CivitasBigInteger m = EL_GAMAL_PUBLIC_KEY_EPRIME.y;

		CivitasBigInteger c1 = BIGINT_A;
		CivitasBigInteger c2 = BIGINT_B;
		ElGamalCiphertext cipherText = new ElGamalCiphertext(c1, c2);

		c1 = c1.modMultiply(g.modPow(y, p), p);
		c2 = c2.modMultiply(m.modPow(y, p), p);

		assertEquals(new ElGamalCiphertext(c1, c2),
				elGamalReencrypt.apply(EL_GAMAL_PUBLIC_KEY_EPRIME, cipherText,
						new ElGamalReencryptFactor(y)));

	}

}
