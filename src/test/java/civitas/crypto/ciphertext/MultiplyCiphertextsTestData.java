package civitas.crypto.ciphertext;

import civitas.crypto.parameters.ElGamalParametersCTestData;
import civitas.crypto.signedciphertext.ElGamalSignedCiphertextC;
import civitas.util.CivitasBigInteger;

public interface MultiplyCiphertextsTestData
		extends ElGamalParametersCTestData {
	ElGamalSignedCiphertextC CIPHERTEXT_2_3 = new ElGamalSignedCiphertextC(
			CivitasBigInteger.valueOf(2), CivitasBigInteger.valueOf(3), null, null);
	ElGamalSignedCiphertextC[][] CIPHERTEXT_MATRIX = new ElGamalSignedCiphertextC[][] {
			{
					CIPHERTEXT_2_3,
					new ElGamalSignedCiphertextC(CivitasBigInteger.valueOf(5),
							CivitasBigInteger.valueOf(7), null, null), },
			{
					new ElGamalSignedCiphertextC(CivitasBigInteger.valueOf(11),
							CivitasBigInteger.valueOf(13), null, null),
					new ElGamalSignedCiphertextC(CivitasBigInteger.valueOf(17),
							CivitasBigInteger.valueOf(19), null, null),

			} };

}
