package civitas.crypto.algorithms;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import civitas.crypto.ElGamalReencryptFactor;
import civitas.crypto.concrete.ConcreteTestBase;
import civitas.crypto.concrete.ElGamalProofDVRC;
import civitas.crypto.concrete.ElGamalReencryptFactorC;
import civitas.util.CivitasBigInteger;
import civitas.util.Tested;

public class ConstructElGamalProofDVRCTest extends ConcreteTestBase
		implements ElGamalProofDVRCTestData {

	@Tested
	ConstructElGamalProofDVRC constructElGamalProofDVRC;

	@Test
	@DisplayName("constructproof works")
	void test1() throws IllegalArgumentException, IOException {
		ElGamalReencryptFactor er = REENCRYPT_FACTOR_RANDOMS_1;
		ElGamalReencryptFactor erPrime = ELGAMAL_REENCRYPT_FACTOR;
		CivitasBigInteger zeta = ((ElGamalReencryptFactorC) erPrime).r
				.modSubtract(((ElGamalReencryptFactorC) er).r, BIGINT_Q);
		ElGamalProofDVRC proof = constructElGamalProofDVRC.apply(
				EL_GAMAL_CIPHERTEXT_E, EL_GAMAL_CIPHERTEXT_A_ENCRYPTED_WITH_FACTOR_A, EL_GAMAL_PUBLIC_KEY,
				EL_GAMAL_PUBLIC_KEY2, zeta);
		assertEquals(EL_GAMAL_PROOF_DVR_XML, proof.toXML());
	}

}
