package civitas.crypto.rsakeypair;

import civitas.crypto.rsapublickey.PublicKeyTestData;

public interface KeyPairTestData extends PublicKeyTestData {
	KeyPair KEYPAIR = new KeyPair(PUBLIC_KEY, PRIVATE_KEY);
}
