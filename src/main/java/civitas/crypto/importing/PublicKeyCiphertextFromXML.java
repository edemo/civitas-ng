package civitas.crypto.importing;

import java.io.IOException;
import java.io.Reader;
import java.util.Base64;

import civitas.common.Util;
import civitas.crypto.PublicKeyCiphertext;
import civitas.crypto.concrete.PublicKeyCiphertextC;

public class PublicKeyCiphertextFromXML {

	public PublicKeyCiphertext apply(Reader r)
			throws IllegalArgumentException, IOException {
		String s = Util
				.unescapeString(Util.readSimpleTag(r, PublicKeyCiphertext.OPENING_TAG));
		return new PublicKeyCiphertextC(Base64.getDecoder().decode(s));
	}

}
