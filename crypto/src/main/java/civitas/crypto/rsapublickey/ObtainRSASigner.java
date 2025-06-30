package civitas.crypto.rsapublickey;

import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

import org.springframework.stereotype.Controller;

import civitas.crypto.Constants;

@Controller
public class ObtainRSASigner implements Constants {

	public java.security.Signature apply()
			throws NoSuchAlgorithmException, NoSuchProviderException {
		java.security.Signature sig = java.security.Signature
				.getInstance(PUBLIC_KEY_SIGNATURE_ALG, PUBLIC_KEY_PROVIDER);
		return sig;
	}

}
