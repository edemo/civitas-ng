package civitas.crypto.privatekey;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import civitas.util.GetReaderForFile;
import civitas.util.ObtainKeyFactory;

@Service
public class ElGamalPrivateKeyFromFile {

	@Autowired
	GetReaderForFile getReaderForFile;

	@Autowired
	ObtainKeyFactory obtainKeyFactory;

	public ElGamalPrivateKey apply(String keyFile)
			throws IOException, FileNotFoundException {
		BufferedReader reader = getReaderForFile.apply(keyFile);
		XmlMapper mapper = new XmlMapper();
		return mapper.readValue(reader, ElGamalPrivateKey.class);

	}

}
