package civitas.crypto.rsapublickey;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;

public class PublicKeyFromFile {

	public PublicKey apply(String keyFile)
			throws IOException, FileNotFoundException {
		XmlMapper mapper = new XmlMapper();
		BufferedReader reader = new BufferedReader(new FileReader(keyFile));
		return mapper.readValue(reader, PublicKey.class);
	}

}
