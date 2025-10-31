package civitas.crypto.decriptionshare.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import civitas.crypto.decriptionshare.ConstructElGamalDecryptionShare;
import civitas.crypto.decriptionshare.ElGamalDecryptionShare;
import civitas.crypto.keypairshare.tests.ElGamalKeyPairShareTestData;
import civitas.crypto.proofdisclog.ConstructElGamalDiscLogEqualityProof;
import civitas.util.CivitasBigInteger;
import io.github.magwas.konveyor.testing.TestBase;

class ConstructElGamalDecryptionShareTest extends TestBase
		implements ElGamalDecryptionShareTestData, ElGamalKeyPairShareTestData {

	@InjectMocks
	ConstructElGamalDecryptionShare constructElGamalDecryptionShare;

	@Mock
	ConstructElGamalDiscLogEqualityProof constructElGamalDiscLogEqualityProof;

	@Test
	@DisplayName("constructDecryptionShare works as expected: "
			+ "ai:=ciphertext.a^key (mod p)"
			+ "proof =  ElGamalDiscLogEqualityProof(params, ciphertext.a, params.g,priv.x)")
	void constructDecryptionShareTest() throws IllegalAccessException {

		CivitasBigInteger key = EL_GAMAL_PRIVATE_KEY_E.x();
		CivitasBigInteger p = EL_GAMAL_PARAMETERS.p;
		CivitasBigInteger c1 = CIPHERTEXT_E.a;

		CivitasBigInteger ai = c1.modPow(key, p);

		ElGamalDecryptionShare actual = constructElGamalDecryptionShare.apply(CIPHERTEXT_E, EL_GAMAL_KEYPAIR_SHARE);
		verify(constructElGamalDiscLogEqualityProof)
				.apply(EL_GAMAL_PARAMETERS, CIPHERTEXT_E.a, BIGINT_G, EL_GAMAL_PRIVATE_KEY_E.x());
		assertEquals(ai, actual.ai());
		assertEquals(EL_GAMAL_DECRYPTION_SHARE, actual);
	}
}
