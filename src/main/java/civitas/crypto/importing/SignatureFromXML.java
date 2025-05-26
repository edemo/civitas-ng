package civitas.crypto.importing;

import java.io.IOException;
import java.io.Reader;
import java.util.Base64;

import civitas.common.Util;
import civitas.crypto.Signature;
import civitas.crypto.concrete.SignatureC;

public class SignatureFromXML {
	public Signature apply(Reader r)
			throws IllegalArgumentException, IOException {
		String s = Util
				.unescapeString(Util.readSimpleTag(r, Signature.OPENING_TAG));
		return new SignatureC(Base64.getDecoder().decode(s));
	}

}
