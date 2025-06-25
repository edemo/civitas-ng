package civitas.common;

import java.io.IOException;
import java.security.spec.InvalidKeySpecException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import civitas.crypto.rsapublickey.PublicKeyFromFile;
import civitas.util.KeyOnWire;

@Service
public class GetServerPublicKey {
	@Autowired
	Configuration configuration;

	@Autowired
	PublicKeyFromFile publicKeyFromFile;

	KeyOnWire bbPublicKey;

	public KeyOnWire apply()
			throws IllegalArgumentException, InvalidKeySpecException, IOException {
		if (null == bbPublicKey)
			bbPublicKey = publicKeyFromFile.apply(configuration.pubKeyFile);
		return bbPublicKey;

	}

}
