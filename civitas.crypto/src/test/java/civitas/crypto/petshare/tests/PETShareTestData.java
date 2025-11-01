package civitas.crypto.petshare.tests;

import civitas.crypto.ciphertext.tests.ElGamalCiphertextTestData;
import civitas.crypto.petshare.PETShare;

public interface PETShareTestData extends ElGamalCiphertextTestData {

	PETShare PET_SHARE = new PETShare(CIPHERTEXT_E, CIPHERTEXT_EPRIME, FACTOR_E);

	PETShare PET_SHARE_RANDOMS0 = new PETShare(CIPHERTEXT_E, CIPHERTEXT_EPRIME, RANDOMS_0);
}
