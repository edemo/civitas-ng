package civitas.crypto.rsapublickey;

import java.security.PublicKey;
import java.util.Base64;

import org.springframework.stereotype.Service;

@Service
public class ConvertPublicKeyToString {

	public String apply(PublicKey pub) {
		return Base64.getEncoder().encodeToString(pub.getEncoded());
	}
}
