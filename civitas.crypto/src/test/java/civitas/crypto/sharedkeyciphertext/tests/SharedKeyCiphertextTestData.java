package civitas.crypto.sharedkeyciphertext.tests;

import civitas.crypto.sharedkeyciphertext.SharedKeyCiphertext;

public interface SharedKeyCiphertextTestData {
	byte[] SHARED_KEY_CIPHERTEXT_BYTES = "shared encrypted".getBytes();
	SharedKeyCiphertext SHARED_KEY_CIPHERTEXT = new SharedKeyCiphertext(SHARED_KEY_CIPHERTEXT_BYTES);
}
