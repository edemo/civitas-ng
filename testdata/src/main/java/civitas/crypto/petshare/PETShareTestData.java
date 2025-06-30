package civitas.crypto.petshare;

import civitas.crypto.ciphertext.ElGamalCiphertextTestData;

public interface PETShareTestData extends ElGamalCiphertextTestData {

	public static final PETShare PET_SHARE = new PETShare(CIPHERTEXT_E,
			CIPHERTEXT_EPRIME, FACTOR_E);

}
