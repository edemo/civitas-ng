package civitas.crypto.petshare;

import civitas.crypto.ciphertext.ElGamalCiphertextTestData;

public interface PETShareTestData extends ElGamalCiphertextTestData {

	public static final PETShare PET_SHARE = new PETShare(CIPHERTEXT_E,
			CIPHERTEXT_EPRIME, FACTOR_E);

	public static final PETShare PET_SHARE_RANDOMS0 = new PETShare(CIPHERTEXT_E,
			CIPHERTEXT_EPRIME, RANDOMS_0);

}
