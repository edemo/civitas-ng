package civitas.crypto.publickey;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;

public class ElGamalPublicKeyFromFile {

	public ElGamalPublicKey apply(String keyFile)
			throws IOException, FileNotFoundException {
		XmlMapper mapper = new XmlMapper();
		BufferedReader reader = new BufferedReader(new FileReader(keyFile));
		return mapper.readValue(reader, ElGamalPublicKey.class);
	}

}
