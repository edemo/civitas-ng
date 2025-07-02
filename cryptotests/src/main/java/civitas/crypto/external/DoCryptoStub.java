package civitas.crypto.external;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import javax.crypto.Cipher;

import civitas.crypto.BasicValuesTestData;
import civitas.crypto.Constants;
import civitas.crypto.publickeyciphertext.PublicKeyCiphertextTestData;
import civitas.crypto.rsapublickey.PublicKeyTestData;
import civitas.crypto.sharedkey.SharedKeyTestData;
import civitas.crypto.sharedkeyciphertext.SharedKeyCiphertextTestData;

class DoCryptoStub
		implements Constants, PublicKeyCiphertextTestData, PublicKeyTestData,
		BasicValuesTestData, SharedKeyTestData, SharedKeyCiphertextTestData {

	public static DoCrypto stub() {
		DoCrypto mock = mock(DoCrypto.class);
		when(mock.apply(PUBLIC_KEY_CIPHER_ALG, PUBLIC_KEY_PROVIDER, PUBLIC_KEY,
				Cipher.ENCRYPT_MODE, SOMESTRING.getBytes()))
				.thenReturn(SOMESTRING_ENCRYPTED_BYTES);
		when(mock.apply(PUBLIC_KEY_CIPHER_ALG, PUBLIC_KEY_PROVIDER, PRIVATE_KEY,
				Cipher.DECRYPT_MODE, SOMESTRING_ENCRYPTED_BYTES))
				.thenReturn(SOMESTRING.getBytes());
		when(mock.apply(SHARED_KEY_CIPHER_ALG, SHARED_KEY_PROVIDER, SHARED_KEY_SPEC,
				Cipher.ENCRYPT_MODE, SOMESTRING.getBytes()))
				.thenReturn(SHARED_KEY_CIPHERTEXT_BYTES);
		when(mock.apply(SHARED_KEY_CIPHER_ALG, SHARED_KEY_PROVIDER, SHARED_KEY_SPEC,
				Cipher.DECRYPT_MODE, SHARED_KEY_CIPHERTEXT_BYTES))
				.thenReturn(SOMESTRING.getBytes());
		return mock;
	}
}
