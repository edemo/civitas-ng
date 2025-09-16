package civitas.crypto.sharedkeyciphertext;

public interface SharedKeyCiphertextTestData {
	byte[] SHARED_KEY_CIPHERTEXT_BYTES = "shared encrypted".getBytes();
	SharedKeyCiphertext SHARED_KEY_CIPHERTEXT = new SharedKeyCiphertext(
			SHARED_KEY_CIPHERTEXT_BYTES);
}
