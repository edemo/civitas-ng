package civitas.crypto.rsakeypair.tests;

import civitas.crypto.rsakeypair.KeyPair;
import civitas.crypto.rsapublickey.tests.PublicKeyTestData;

public interface KeyPairTestData extends PublicKeyTestData {
	KeyPair KEYPAIR = new KeyPair(PUBLIC_KEY, PRIVATE_KEY);
}
