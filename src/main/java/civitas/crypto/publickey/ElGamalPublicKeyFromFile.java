package civitas.crypto.publickey;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import civitas.util.Use;

public class ElGamalPublicKeyFromFile {

	@Use
	ElGamalPublicKeyFromXML elGamalPublicKeyFromXML;

	public ElGamalPublicKey apply(String keyFile)
			throws IOException, FileNotFoundException {
		return elGamalPublicKeyFromXML
				.apply(new BufferedReader(new FileReader(keyFile)));
	}

}
