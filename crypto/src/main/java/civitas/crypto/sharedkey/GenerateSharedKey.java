package civitas.crypto.sharedkey;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import civitas.crypto.sharedkey.SharedKey;

@Controller
public class GenerateSharedKey {
	@Autowired
	GetSharedKeyGenerator getSharedKeyGenerator;

	public SharedKey apply(int keyLength) {
		SecretKey k = getSharedKeyGenerator.apply(keyLength).generateKey();
		return new SharedKey(k, "sharedKey-civitas");
	}

}
