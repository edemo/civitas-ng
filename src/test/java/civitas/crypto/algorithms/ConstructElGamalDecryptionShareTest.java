package civitas.crypto.algorithms;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import civitas.crypto.concrete.ConcreteTestBase;
import civitas.crypto.concrete.ElGamalDecryptionShareC;
import civitas.crypto.concrete.ElGamalDecryptionShareTestData;
import civitas.util.CivitasBigInteger;
import civitas.util.Tested;
import civitas.util.Use;

public class ConstructElGamalDecryptionShareTest extends ConcreteTestBase
		implements ElGamalDecryptionShareTestData {

	@Tested
	ConstructElGamalDecryptionShare constructElGamalDecryptionShare;

	@Use
	CryptoHash hash;
	@Use
	ElGamalEncryptTest elGamalEncrypt;

	@Test
	@DisplayName("constructDecryptionShare works as expected: "
			+ "ai:=ciphertext.a^key (mod p)"
			+ "proof =  ElGamalDiscLogEqualityProof(params, ciphertext.a, params.g,priv.x)")
	void constructDecryptionShareTest() throws Exception {

		CivitasBigInteger key = ELGAMAL_PRIVATE_KEY_E.x;
		CivitasBigInteger p = EL_GAMAL_PARAMETERS.p;
		CivitasBigInteger c1 = CIPHERTEXT_E.a;

		CivitasBigInteger ai = c1.modPow(key, p);

		ElGamalDecryptionShareC actual = (ElGamalDecryptionShareC) constructElGamalDecryptionShare
				.apply(CIPHERTEXT_E, EL_GAMAL_KEYPAIR_SHARE_NO_PROOF);
		verify(constructElGamalDecryptionShare.constructElGamalDiscLogEqualityProof)
				.apply(EL_GAMAL_PARAMETERS, CIPHERTEXT_E.a, BIGINT_G,
						ELGAMAL_PRIVATE_KEY_E.x);
		assertEquals(ai, actual.ai);
		assertEquals(EL_GAMAL_DECRYPTION_SHARE_XML, actual.toXML());

	}

}
