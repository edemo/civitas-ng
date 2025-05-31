package civitas.crypto.signature;

import java.io.IOException;
import java.io.Reader;
import java.util.Base64;

import civitas.common.Util;
import civitas.crypto.Constants;

public class SignatureFromXML implements Constants {
	public Signature apply(Reader r)
			throws IllegalArgumentException, IOException {
		String s = Util.unescapeString(Util.readSimpleTag(r, SignatureOPENING_TAG));
		return new Signature(Base64.getDecoder().decode(s));
	}

}
