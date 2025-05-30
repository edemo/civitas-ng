package civitas.crypto.rsaprivatekey;

import java.io.IOException;
import java.io.Reader;
import java.util.Base64;

import civitas.common.Util;
import civitas.crypto.CryptoFactoryC;

public class PrivateKeyFromXML {
	public PrivateKey apply(Reader r)
			throws IllegalArgumentException, IOException {
		String s = Util
				.unescapeString(Util.readSimpleTag(r, PrivateKeyC.OPENING_TAG));
		byte[] bs = Base64.getDecoder().decode(s);

		CryptoFactoryC factory = CryptoFactoryC.singleton();
		return new PrivateKeyC(factory.privateKeyFromBytes(bs));
	}

}
