package civitas.external;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import civitas.crypto.PublicKey;
import civitas.crypto.importing.PublicKeyFromXML;
import civitas.util.Use;

public class PublicKeyFromFile {
	@Use
	PublicKeyFromXML publicKeyFromXML;

	public PublicKey apply(String keyFile)
			throws IOException, FileNotFoundException {
		return publicKeyFromXML.apply(new BufferedReader(new FileReader(keyFile)));
	}

}
