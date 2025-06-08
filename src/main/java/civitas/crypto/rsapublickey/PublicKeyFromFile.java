package civitas.crypto.rsapublickey;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import civitas.util.GetReaderForFile;
import civitas.util.KeyOnWire;
import civitas.util.ObtainKeyFactory;
import civitas.util.Use;

public class PublicKeyFromFile {
	@Use
	GetReaderForFile getReaderForFile;
	@Use
	ObtainKeyFactory obtainKeyFactory;

	public PublicKey apply(String keyFile)
			throws IOException, FileNotFoundException, InvalidKeySpecException {
		XmlMapper mapper = new XmlMapper();
		BufferedReader reader = getReaderForFile.apply(keyFile);
		KeyOnWire fromWire = mapper.readValue(reader, KeyOnWire.class);
		return new PublicKey(
				obtainKeyFactory.apply().generatePublic(
						new X509EncodedKeySpec(Base64.getDecoder().decode(fromWire.key))),
				fromWire.name);
	}

}
