package civitas.crypto.sharedkey;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import civitas.crypto.CryptoBase;

@Controller
public class GenerateSharedKey {
	@Autowired
	CryptoBase cryptoBase;

	public SharedKey apply(final int keyLength) {
		SecretKey k = cryptoBase.getSharedKeyGenerator(keyLength).generateKey();
		return new SharedKey(k, "sharedKey-civitas");
	}
}
