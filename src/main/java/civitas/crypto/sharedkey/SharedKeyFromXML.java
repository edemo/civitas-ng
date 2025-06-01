package civitas.crypto.sharedkey;

import java.io.IOException;
import java.io.Reader;
import java.util.Base64;

import civitas.common.Util;
import civitas.crypto.Constants;
import civitas.util.Use;

public class SharedKeyFromXML implements Constants {

	@Use
	CreateSharedKeyFromBytes createSharedKeyFromBytes;

	public SharedKey apply(Reader r)
			throws IllegalArgumentException, IOException {
		Util.swallowTag(r, SharedKeyOPENING_TAG);
		String name = Util.readSimpleTag(r, "n");
		String s = Util.unescapeString(Util.readSimpleTag(r, "k"));
		Util.swallowEndTag(r, SharedKeyOPENING_TAG);

		byte[] bs = Base64.getDecoder().decode(s);
		return new SharedKey(createSharedKeyFromBytes.apply(bs), name);
	}

}
