package civitas.crypto.rsapublickey;

import java.io.IOException;
import java.io.Reader;

import civitas.common.Util;
import civitas.crypto.Constants;
import civitas.util.Use;

public class PublicKeyFromXML implements Constants {
	@Use
	CreatePublicKeyFromBytes createPublicKeyFromBytes;

	public PublicKey apply(Reader r)
			throws IllegalArgumentException, IOException {
		Util.swallowTag(r, PublicKeyOPENING_TAG);
		String name = Util.readSimpleTag(r, "name");
		String s = Util.unescapeString(Util.readSimpleTag(r, "key"));
		Util.swallowEndTag(r, PublicKeyOPENING_TAG);

		byte[] bs = java.util.Base64.getDecoder().decode(s);
		return new PublicKey(createPublicKeyFromBytes.apply(bs), name);
	}

}
