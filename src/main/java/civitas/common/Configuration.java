package civitas.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import civitas.crypto.rsapublickey.PublicKeyFromFile;

@Component
public class Configuration {

	@Autowired
	PublicKeyFromFile publicKeyFromFile;

	@Value("${pubKeyFile}")
	public String pubKeyFile;
	@Value("${privKeyFile}")
	public String privKeyFile;

}
