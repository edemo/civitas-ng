package civitas.crypto.publickey;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import civitas.util.GetReaderForFile;
import civitas.util.Use;

public class ElGamalPublicKeyFromFile {

	@Use
	GetReaderForFile getReaderForFile;

	public ElGamalPublicKey apply(String keyFile)
			throws IOException, FileNotFoundException {
		XmlMapper mapper = new XmlMapper();
		BufferedReader reader = getReaderForFile.apply(keyFile);
		return mapper.readValue(reader, ElGamalPublicKey.class);
	}

}
