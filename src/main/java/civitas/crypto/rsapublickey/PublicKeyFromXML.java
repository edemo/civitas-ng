package civitas.crypto.rsapublickey;

import java.io.IOException;
import java.io.Reader;

import civitas.common.Util;
import civitas.crypto.CryptoFactoryC;

public class PublicKeyFromXML {
	public PublicKey apply(Reader r)
			throws IllegalArgumentException, IOException {
		Util.swallowTag(r, PublicKey.OPENING_TAG);
		String name = Util.readSimpleTag(r, "name");
		String s = Util.unescapeString(Util.readSimpleTag(r, "key"));
		Util.swallowEndTag(r, PublicKey.OPENING_TAG);

		byte[] bs = java.util.Base64.getDecoder().decode(s);
		CryptoFactoryC factory = CryptoFactoryC.singleton();
		return new PublicKeyC(factory.publicKeyFromBytes(bs), name);
	}

}
