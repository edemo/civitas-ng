package civitas.crypto.signedciphertext;

import java.io.IOException;
import java.io.Reader;
import java.util.Base64;

import civitas.common.Util;
import civitas.crypto.Constants;
import civitas.crypto.sharedkeyciphertext.SharedKeyCiphertext;

public class SharedKeyCiphertextFromXML implements Constants {

	public SharedKeyCiphertext apply(Reader r)
			throws IllegalArgumentException, IOException {
		String s = Util
				.unescapeString(Util.readSimpleTag(r, SharedKeyCiphertextOPENING_TAG));
		return new SharedKeyCiphertext(Base64.getDecoder().decode(s));
	}

}
