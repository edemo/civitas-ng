package civitas.crypto.algorithms;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import civitas.crypto.concrete.ConcreteTestBase;
import civitas.crypto.concrete.ElGamalCiphertextC;
import civitas.crypto.concrete.ElGamalCiphertextCTestData;
import civitas.crypto.concrete.ElGamalMsgC;
import civitas.util.CivitasBigInteger;
import civitas.util.Tested;

public class ElGamalEncryptTest extends ConcreteTestBase
		implements ElGamalCiphertextCTestData {

	@Tested
	ElGamalEncrypt elGamalEncrypt;

	@Test
	@DisplayName("elGamalEncrypt with a random factor works as expected")
	void test0_1() throws Exception {

		CivitasBigInteger y = RANDOMS_0;
		ElGamalMsgC msg = new ElGamalMsgC(BIGINT_B, EL_GAMAL_PARAMETERS);
		CivitasBigInteger h = EL_GAMAL_PUBLIC_KEY.y;
		CivitasBigInteger c1 = BIGINT_G.modPow(y, BIGINT_P);
		CivitasBigInteger s = h.modPow(y, BIGINT_P);
		CivitasBigInteger c2 = msg.m.modMultiply(s, BIGINT_P);
		ElGamalCiphertextC encrypt = (ElGamalCiphertextC) elGamalEncrypt
				.apply(EL_GAMAL_PUBLIC_KEY, msg);
		assertEquals(c1, encrypt.a);
		assertEquals(c2, encrypt.b);

	}

}
