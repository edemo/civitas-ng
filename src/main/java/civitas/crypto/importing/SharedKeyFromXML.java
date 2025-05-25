package civitas.crypto.importing;

import java.io.IOException;
import java.io.Reader;
import java.util.Base64;

import civitas.common.Util;
import civitas.crypto.SharedKey;
import civitas.crypto.concrete.CryptoFactoryC;
import civitas.crypto.concrete.SharedKeyC;

public class SharedKeyFromXML {

	public SharedKey apply(Reader r)
			throws IllegalArgumentException, IOException {
		Util.swallowTag(r, SharedKey.OPENING_TAG);
		String name = Util.readSimpleTag(r, "n");
		String s = Util.unescapeString(Util.readSimpleTag(r, "k"));
		Util.swallowEndTag(r, SharedKey.OPENING_TAG);

		byte[] bs = Base64.getDecoder().decode(s);
		CryptoFactoryC factory = CryptoFactoryC.singleton();
		return new SharedKeyC(factory.sharedKeyFromBytes(bs), name);
	}

}
