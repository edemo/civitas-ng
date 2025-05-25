package civitas.crypto.algorithms;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import civitas.crypto.concrete.ConcreteTestBase;
import civitas.crypto.concrete.ElGamalCiphertextCTestData;
import civitas.crypto.concrete.ElGamalMsgC;
import civitas.crypto.concrete.ElGamalReencryptFactorC;
import civitas.crypto.concrete.ElGamalSignedCiphertextC;
import civitas.util.CivitasBigInteger;
import civitas.util.Tested;
import civitas.util.Use;

public class SignAndEncryptTest extends ConcreteTestBase
		implements ElGamalCiphertextCTestData {

	@Tested
	SignAndEncrypt signAndEncrypt;

	@Use
	CryptoHash cryptoHash;

	@Test
	@DisplayName("elGamalSignedEncrypt works as expected:" + "s:= random, "
			+ "a:=g^y (mod p), " + "b:=m*key^y (mod p), "
			+ "c:=hash(g^s,a,b,env) % q, " + "d:=s+c*y (mod q)")
	void elGamalSignedEncryptTest() throws Exception {

		CivitasBigInteger s = RANDOMS_0;
		CivitasBigInteger y = SOME_INT_BIG;
		ElGamalMsgC msg = new ElGamalMsgC(BIGINT_B, EL_GAMAL_PARAMETERS);
		CivitasBigInteger g = EL_GAMAL_PARAMETERS.g;
		CivitasBigInteger p = EL_GAMAL_PARAMETERS.p;
		CivitasBigInteger q = EL_GAMAL_PARAMETERS.q;
		CivitasBigInteger key = EL_GAMAL_PUBLIC_KEY.y;
		byte[] env = SOMESTRING_EXTENDED.getBytes();

		CivitasBigInteger a = g.modPow(y, p);
		CivitasBigInteger b = msg.m.modMultiply(key.modPow(y, p), p);
		CivitasBigInteger c = cryptoHash.apply(g.modPow(s, p), a, b, env).mod(q);
		CivitasBigInteger d = s.modAdd(c.modMultiply(y, q), q);

		ElGamalSignedCiphertextC encrypt = (ElGamalSignedCiphertextC) signAndEncrypt
				.apply(EL_GAMAL_PUBLIC_KEY, msg, new ElGamalReencryptFactorC(y), env);

		assertEquals(a, encrypt.a);
		assertEquals(b, encrypt.b);
		assertEquals(c, encrypt.c);
		assertEquals(d, encrypt.d);

	}

}
