package civitas.crypto.importing;

import java.io.IOException;
import java.io.Reader;

import civitas.common.Util;
import civitas.crypto.PublicKey;
import civitas.crypto.concrete.CryptoFactoryC;
import civitas.crypto.concrete.PublicKeyC;

public class PublicKeyFromXML {
	public PublicKey apply(Reader r)
			throws IllegalArgumentException, IOException {
		Util.swallowTag(r, PublicKeyC.OPENING_TAG);
		String name = Util.readSimpleTag(r, "name");
		String s = Util.unescapeString(Util.readSimpleTag(r, "key"));
		Util.swallowEndTag(r, PublicKeyC.OPENING_TAG);

		byte[] bs = java.util.Base64.getDecoder().decode(s);
		CryptoFactoryC factory = CryptoFactoryC.singleton();
		return new PublicKeyC(factory.publicKeyFromBytes(bs), name);
	}

}
