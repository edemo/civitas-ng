package civitas.crypto.decriptionshare;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import civitas.common.TestBase;
import civitas.crypto.algorithms.CryptoHash;
import civitas.crypto.ciphertext.ElGamalEncryptTest;
import civitas.util.CivitasBigInteger;
import civitas.util.Tested;
import civitas.util.Use;

public class ConstructElGamalDecryptionShareTest extends TestBase
		implements ElGamalDecryptionShareTestData {

	@Tested
	ConstructElGamalDecryptionShare constructElGamalDecryptionShare;

	@Use
	CryptoHash hash;
	@Use
	ElGamalEncryptTest elGamalEncrypt;
	@Use
	ElGamalDecryptionShareToXML elGamalDecryptionShareToXML;

	@Test
	@DisplayName("constructDecryptionShare works as expected: "
			+ "ai:=ciphertext.a^key (mod p)"
			+ "proof =  ElGamalDiscLogEqualityProof(params, ciphertext.a, params.g,priv.x)")
	void constructDecryptionShareTest() throws Exception {

		CivitasBigInteger key = EL_GAMAL_PRIVATE_KEY_E.x;
		CivitasBigInteger p = EL_GAMAL_PARAMETERS.p;
		CivitasBigInteger c1 = CIPHERTEXT_E.a;

		CivitasBigInteger ai = c1.modPow(key, p);

		ElGamalDecryptionShare actual = constructElGamalDecryptionShare
				.apply(CIPHERTEXT_E, EL_GAMAL_KEYPAIR_SHARE);
		verify(constructElGamalDecryptionShare.constructElGamalDiscLogEqualityProof)
				.apply(EL_GAMAL_PARAMETERS, CIPHERTEXT_E.a, BIGINT_G,
						EL_GAMAL_PRIVATE_KEY_E.x);
		assertEquals(ai, actual.ai);
		assertEquals(EL_GAMAL_DECRYPTION_SHARE_XML,
				elGamalDecryptionShareToXML.apply(actual));

	}

}
