package civitas.crypto.importing;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import civitas.crypto.ElGamalPrivateKey;
import civitas.util.Use;

public class ElGamalPrivateKeyFromFile {
	@Use
	ElGamalPrivateKeyFromXML elGamalPrivateKeyFromXML;

	public ElGamalPrivateKey apply(String keyFile)
			throws IOException, FileNotFoundException {
		return elGamalPrivateKeyFromXML
				.apply(new BufferedReader(new FileReader(keyFile)));
	}

}
