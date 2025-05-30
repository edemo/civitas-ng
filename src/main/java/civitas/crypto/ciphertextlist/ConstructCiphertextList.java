package civitas.crypto.ciphertextlist;

import java.util.Arrays;

import civitas.crypto.ciphertext.ElGamalCiphertext;

public class ConstructCiphertextList {
	public CiphertextList apply(ElGamalCiphertext[] array) {
		return new CiphertextList(Arrays.asList(array));

	}
}
