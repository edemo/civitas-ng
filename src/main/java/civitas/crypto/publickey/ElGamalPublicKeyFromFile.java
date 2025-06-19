package civitas.crypto.publickey;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import civitas.util.GetReaderForFile;

@Service
public class ElGamalPublicKeyFromFile {

	@Autowired
	GetReaderForFile getReaderForFile;

	public ElGamalPublicKey apply(String keyFile)
			throws IOException, FileNotFoundException {
		XmlMapper mapper = new XmlMapper();
		BufferedReader reader = getReaderForFile.apply(keyFile);
		return mapper.readValue(reader, ElGamalPublicKey.class);
	}

}
