package civitas.crypto.publickeyciphertext;

import java.io.IOException;
import java.io.Reader;
import java.util.Base64;

import civitas.common.Util;

public class PublicKeyCiphertextFromXML {

	public PublicKeyCiphertext apply(Reader r)
			throws IllegalArgumentException, IOException {
		String s = Util
				.unescapeString(Util.readSimpleTag(r, PublicKeyCiphertext.OPENING_TAG));
		return new PublicKeyCiphertext(Base64.getDecoder().decode(s));
	}

}
