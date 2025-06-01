package civitas.crypto.rsaprivatekey;

import java.io.IOException;
import java.io.Reader;
import java.util.Base64;

import civitas.common.Util;
import civitas.crypto.Constants;
import civitas.util.Use;

public class PrivateKeyFromXML implements Constants {
	@Use
	CreatePrivateKeyFromBytes createPrivateKeyFromBytes;

	public PrivateKey apply(Reader r)
			throws IllegalArgumentException, IOException {
		String s = Util
				.unescapeString(Util.readSimpleTag(r, PrivateKeyOPENING_TAG));
		byte[] bs = Base64.getDecoder().decode(s);

		return new PrivateKey(createPrivateKeyFromBytes.apply(bs));
	}

}
