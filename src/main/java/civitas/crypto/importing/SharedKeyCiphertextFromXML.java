package civitas.crypto.importing;

import java.io.IOException;
import java.io.Reader;
import java.util.Base64;

import civitas.common.Util;
import civitas.crypto.SharedKeyCiphertext;
import civitas.crypto.concrete.SharedKeyCiphertextC;

public class SharedKeyCiphertextFromXML {

	public SharedKeyCiphertext apply(Reader r)
			throws IllegalArgumentException, IOException {
		String s = Util
				.unescapeString(Util.readSimpleTag(r, SharedKeyCiphertext.OPENING_TAG));
		return new SharedKeyCiphertextC(Base64.getDecoder().decode(s));
	}

}
