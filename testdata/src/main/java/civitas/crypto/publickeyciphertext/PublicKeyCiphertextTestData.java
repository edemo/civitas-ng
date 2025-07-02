package civitas.crypto.publickeyciphertext;

public interface PublicKeyCiphertextTestData {
	byte[] SOMESTRING_ENCRYPTED_BYTES = "SOMESTRING encrypted".getBytes();
	PublicKeyCiphertext SOMESTRING_ENCRYPTED = new PublicKeyCiphertext(
			SOMESTRING_ENCRYPTED_BYTES);

}
