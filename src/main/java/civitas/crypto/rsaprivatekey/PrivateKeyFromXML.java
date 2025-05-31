package civitas.crypto.rsaprivatekey;

import java.io.IOException;
import java.io.Reader;
import java.util.Base64;

import civitas.common.Util;
import civitas.crypto.Constants;
import civitas.crypto.CryptoFactoryC;

public class PrivateKeyFromXML implements Constants {
	public PrivateKey apply(Reader r)
			throws IllegalArgumentException, IOException {
		String s = Util
				.unescapeString(Util.readSimpleTag(r, PrivateKeyOPENING_TAG));
		byte[] bs = Base64.getDecoder().decode(s);

		CryptoFactoryC factory = CryptoFactoryC.singleton();
		return new PrivateKey(factory.privateKeyFromBytes(bs));
	}

}
