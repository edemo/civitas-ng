package civitas.crypto.keyshare;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import civitas.crypto.ConcreteTestBase;
import civitas.crypto.CryptoException;
import civitas.crypto.keys.ElGamalKeyShareTestData;
import civitas.crypto.publickey.ElGamalPublicKey;
import civitas.util.Tested;

public class CombineKeySharesTest extends ConcreteTestBase
		implements ElGamalKeyShareTestData {

	@Tested
	CombineKeyShares combineKeyShares;

	@Test
	@DisplayName("combines an array of key shares to one "
			+ "by multiplying the public keys")
	void test() throws CryptoException {
		ElGamalPublicKey actual = (ElGamalPublicKey) combineKeyShares
				.apply(KEY_SHARES);
		assertEquals(EL_GAMAL_PUBLIC_KEY_E.y.multiply(EL_GAMAL_PUBLIC_KEY_EPRIME.y),
				actual.y);
	}

	@Test
	@DisplayName("verifies the proofs")
	void test_1() throws CryptoException {
		combineKeyShares.apply(KEY_SHARES);
		verify(combineKeyShares.verifyElGamalKeyShare).apply(EL_GAMAL_KEY_SHARE_E);
		verify(combineKeyShares.verifyElGamalKeyShare)
				.apply(EL_GAMAL_KEY_SHARE_EPRIME);
	}

	@Test
	@DisplayName("returns null if shares is null")
	void test1() throws CryptoException {
		assertEquals(null, combineKeyShares.apply(null));
	}

	@Test
	@DisplayName("if a key share does not verify, throws CryptoException")
	void test2() throws CryptoException {
		assertThrows(CryptoException.class, () -> combineKeyShares
				.apply(new ElGamalKeyShare[] { EL_GAMAL_KEY_SHARE_NULLPROOF }));
	}

	@Test
	@DisplayName("if a key share is invalid throws CryptoException")
	void test3() throws CryptoException {
		assertThrows(CryptoException.class,
				() -> combineKeyShares.apply(new ElGamalKeyShare[] { null }));
	}

}
