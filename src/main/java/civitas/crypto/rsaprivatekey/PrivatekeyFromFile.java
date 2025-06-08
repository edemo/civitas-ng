package civitas.crypto.rsaprivatekey;

import java.io.BufferedReader;
import java.io.IOException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import civitas.util.GetReaderForFile;
import civitas.util.KeyOnWire;
import civitas.util.ObtainKeyFactory;
import civitas.util.Use;

public class PrivatekeyFromFile {
	@Use
	GetReaderForFile getReaderForFile;

	@Use
	ObtainKeyFactory obtainKeyFactory;

	public PrivateKey apply(String keyFile)
			throws IllegalArgumentException, IOException, InvalidKeySpecException {
		XmlMapper mapper = new XmlMapper();
		BufferedReader reader = getReaderForFile.apply(keyFile);
		KeyOnWire valueOnWire = mapper.readValue(reader, KeyOnWire.class);
		return new PrivateKey(obtainKeyFactory.apply().generatePrivate(
				new PKCS8EncodedKeySpec(Base64.getDecoder().decode(valueOnWire.key))));
	}

}
