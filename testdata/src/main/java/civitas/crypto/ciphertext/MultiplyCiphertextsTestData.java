package civitas.crypto.ciphertext;

import civitas.crypto.parameters.ElGamalParametersTestData;
import civitas.util.CivitasBigInteger;

public interface MultiplyCiphertextsTestData extends ElGamalParametersTestData {
	ElGamalCiphertext CIPHERTEXT_2_3 = new ElGamalCiphertext(
			CivitasBigInteger.valueOf(2), CivitasBigInteger.valueOf(3));
	ElGamalCiphertext[][] CIPHERTEXT_MATRIX = new ElGamalCiphertext[][] {
			{
					CIPHERTEXT_2_3,
					new ElGamalCiphertext(CivitasBigInteger.valueOf(5),
							CivitasBigInteger.valueOf(7)), },
			{
					new ElGamalCiphertext(CivitasBigInteger.valueOf(11),
							CivitasBigInteger.valueOf(13)),
					new ElGamalCiphertext(CivitasBigInteger.valueOf(17),
							CivitasBigInteger.valueOf(19)),

			} };

}
