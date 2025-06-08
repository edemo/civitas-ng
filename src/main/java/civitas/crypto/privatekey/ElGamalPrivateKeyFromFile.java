package civitas.crypto.privatekey;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import civitas.util.GetReaderForFile;
import civitas.util.ObtainKeyFactory;
import civitas.util.Use;

public class ElGamalPrivateKeyFromFile {

	@Use
	GetReaderForFile getReaderForFile;

	@Use
	ObtainKeyFactory obtainKeyFactory;

	public ElGamalPrivateKey apply(String keyFile)
			throws IOException, FileNotFoundException {
		BufferedReader reader = getReaderForFile.apply(keyFile);
		XmlMapper mapper = new XmlMapper();
		return mapper.readValue(reader, ElGamalPrivateKey.class);

	}

}
