package civitas.common;

import java.io.IOException;
import java.security.spec.InvalidKeySpecException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import civitas.crypto.rsaprivatekey.PrivateKey;
import civitas.crypto.rsaprivatekey.PrivatekeyFromFile;

@Service
public class GetServerPrivateKey {

	@Autowired
	Configuration configuration;

	@Autowired
	PrivatekeyFromFile privateKeyFromFile;

	PrivateKey bbPrivateKey;

	public PrivateKey apply()
			throws IllegalArgumentException, InvalidKeySpecException, IOException {
		if (null == bbPrivateKey)
			bbPrivateKey = privateKeyFromFile.apply(configuration.privKeyFile);
		return bbPrivateKey;

	}
}
