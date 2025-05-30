package civitas.crypto.votecapability;

import java.io.IOException;
import java.io.Reader;

import civitas.common.Util;
import civitas.crypto.CryptoFactoryC;

public class VoteCapabilityFromXML {

	public VoteCapabilityC apply(Reader r)
			throws IllegalArgumentException, IOException {
		String s = Util
				.unescapeString(Util.readSimpleTag(r, VoteCapability.OPENING_TAG));
		return new VoteCapabilityC(CryptoFactoryC.stringToBigInt(s));
	}

}
