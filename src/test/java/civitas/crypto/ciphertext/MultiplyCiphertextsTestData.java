package civitas.crypto.ciphertext;

import civitas.crypto.parameters.ElGamalParametersCTestData;
import civitas.crypto.signedciphertext.ElGamalSignedCiphertext;
import civitas.util.CivitasBigInteger;

public interface MultiplyCiphertextsTestData
		extends ElGamalParametersCTestData {
	ElGamalSignedCiphertext CIPHERTEXT_2_3 = new ElGamalSignedCiphertext(
			CivitasBigInteger.valueOf(2), CivitasBigInteger.valueOf(3), null, null);
	ElGamalSignedCiphertext[][] CIPHERTEXT_MATRIX = new ElGamalSignedCiphertext[][] {
			{
					CIPHERTEXT_2_3,
					new ElGamalSignedCiphertext(CivitasBigInteger.valueOf(5),
							CivitasBigInteger.valueOf(7), null, null), },
			{
					new ElGamalSignedCiphertext(CivitasBigInteger.valueOf(11),
							CivitasBigInteger.valueOf(13), null, null),
					new ElGamalSignedCiphertext(CivitasBigInteger.valueOf(17),
							CivitasBigInteger.valueOf(19), null, null),

			} };

}
