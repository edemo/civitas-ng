package civitas.crypto.privatekey;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;

public class ElGamalPrivateKeyFromFile {

	public ElGamalPrivateKey apply(String keyFile)
			throws IOException, FileNotFoundException {
		BufferedReader reader = new BufferedReader(new FileReader(keyFile));
		XmlMapper mapper = new XmlMapper();
		return mapper.readValue(reader, ElGamalPrivateKey.class);

	}

}
