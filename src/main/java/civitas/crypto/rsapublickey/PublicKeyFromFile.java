package civitas.crypto.rsapublickey;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import civitas.crypto.CryptoBase;
import civitas.util.GetReaderForFile;
import civitas.util.KeyOnWire;

@Service
public class PublicKeyFromFile {
	@Autowired
	GetReaderForFile getReaderForFile;
	@Autowired
	CryptoBase cryptoBase;

	public PublicKey apply(String keyFile)
			throws IOException, FileNotFoundException, InvalidKeySpecException {
		XmlMapper mapper = new XmlMapper();
		BufferedReader reader = getReaderForFile.apply(keyFile);
		KeyOnWire fromWire = mapper.readValue(reader, KeyOnWire.class);
		return new PublicKey(
				cryptoBase.publicKeyFactory.generatePublic(
						new X509EncodedKeySpec(Base64.getDecoder().decode(fromWire.key))),
				fromWire.name);
	}

}
