package civitas.crypto.rsaprivatekey;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;

public class PrivatekeyFromFile {

	public PrivateKey apply(String keyFile)
			throws IllegalArgumentException, IOException {
		XmlMapper mapper = new XmlMapper();
		BufferedReader reader = new BufferedReader(new FileReader(keyFile));
		return mapper.readValue(reader, PrivateKey.class);
	}

}
