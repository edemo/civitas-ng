package civitas.crypto.ciphertextlist;

import java.util.List;

import civitas.crypto.ciphertext.ElGamalCiphertextTestData;

public interface ElGamalCiphertextListTestData
		extends ElGamalCiphertextTestData {
	CiphertextList CIPHERTEXTLIST_ZEROSIZED = new CiphertextList();
	CiphertextList CIPHERTEXTLIST_ONEINSIDE = new CiphertextList(
			List.of(CIPHERTEXT_E));
	CiphertextList CIPHERTEXTLIST = new CiphertextList(
			List.of(CIPHERTEXT_E, CIPHERTEXT_EPRIME));

}
