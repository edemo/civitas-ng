package civitas.crypto.publickeyciphertext;

import java.io.IOException;
import java.io.Reader;
import java.util.Base64;

import civitas.common.Util;
import civitas.crypto.Constants;

public class PublicKeyCiphertextFromXML implements Constants {

	public PublicKeyCiphertext apply(Reader r)
			throws IllegalArgumentException, IOException {
		String s = Util
				.unescapeString(Util.readSimpleTag(r, PublicKeyCiphertextOPENING_TAG));
		return new PublicKeyCiphertext(Base64.getDecoder().decode(s));
	}

}
