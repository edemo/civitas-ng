package civitas.crypto.privatekey;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import civitas.util.Use;

public class PrivatekeyFromFile {
	@Use
	PrivateKeyFromXML privateKeyFromXML;

	public PrivateKey apply(String keyFile)
			throws IllegalArgumentException, IOException {
		return privateKeyFromXML.apply(new BufferedReader(new FileReader(keyFile)));
	}

}
