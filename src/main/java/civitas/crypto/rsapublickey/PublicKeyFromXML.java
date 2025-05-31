package civitas.crypto.rsapublickey;

import java.io.IOException;
import java.io.Reader;

import civitas.common.Util;
import civitas.crypto.Constants;
import civitas.crypto.CryptoFactoryC;

public class PublicKeyFromXML implements Constants {
	public PublicKey apply(Reader r)
			throws IllegalArgumentException, IOException {
		Util.swallowTag(r, PublicKeyOPENING_TAG);
		String name = Util.readSimpleTag(r, "name");
		String s = Util.unescapeString(Util.readSimpleTag(r, "key"));
		Util.swallowEndTag(r, PublicKeyOPENING_TAG);

		byte[] bs = java.util.Base64.getDecoder().decode(s);
		CryptoFactoryC factory = CryptoFactoryC.singleton();
		return new PublicKey(factory.publicKeyFromBytes(bs), name);
	}

}
