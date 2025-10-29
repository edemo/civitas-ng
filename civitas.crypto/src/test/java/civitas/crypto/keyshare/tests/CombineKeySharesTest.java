package civitas.crypto.keyshare.tests;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import civitas.crypto.CryptoException;
import civitas.crypto.keys.tests.ElGamalKeyShareTestData;
import civitas.crypto.keyshare.CombineKeyShares;
import civitas.crypto.keyshare.ElGamalKeyShare;
import civitas.crypto.keyshare.VerifyElGamalKeyShare;
import civitas.crypto.publickey.ElGamalPublicKey;
import io.github.magwas.konveyor.testing.TestBase;
import io.github.magwas.konveyor.testing.TestUtil;

class CombineKeySharesTest extends TestBase implements ElGamalKeyShareTestData {

	@InjectMocks
	CombineKeyShares combineKeyShares;

	@Test
	@DisplayName("combines an array of key shares to one " + "by multiplying the public keys")
	void test() throws CryptoException {
		ElGamalPublicKey actual = combineKeyShares.apply(KEY_SHARES);
		assertEquals(EL_GAMAL_PUBLIC_KEY_E.y.multiply(EL_GAMAL_PUBLIC_KEY_EPRIME.y), actual.y);
	}

	@Test
	@DisplayName("verifies the proofs")
	void test_1() throws CryptoException, IllegalAccessException {
		combineKeyShares.apply(KEY_SHARES);
		VerifyElGamalKeyShare verifyElGamalKeyShare =
				TestUtil.dependency(combineKeyShares, VerifyElGamalKeyShare.class);
		verify(verifyElGamalKeyShare).apply(EL_GAMAL_KEY_SHARE_E);
		verify(verifyElGamalKeyShare).apply(EL_GAMAL_KEY_SHARE_EPRIME);
	}

	@Test
	@DisplayName("returns null if shares is null")
	void test1() throws CryptoException {
		assertNull(combineKeyShares.apply((ElGamalKeyShare[]) null));
	}

	@Test
	@DisplayName("if a key share does not verify, throws CryptoException")
	void test2() {
		assertThrows(CryptoException.class, () -> combineKeyShares.apply(EL_GAMAL_KEY_SHARE_NOT_GOOD_PUBKEY_TYPE));
	}

	@Test
	@DisplayName("if a key share is invalid throws CryptoException")
	void test3() {
		assertThrows(CryptoException.class, () -> combineKeyShares.apply(new ElGamalKeyShare[] {null}));
	}

	@Test
	@DisplayName("returns null if shares is empty")
	void test4() throws CryptoException {
		assertNull(combineKeyShares.apply());
	}
}
