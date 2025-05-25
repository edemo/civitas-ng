package civitas.crypto.importing;

import java.io.IOException;
import java.io.Reader;

import civitas.common.Util;
import civitas.crypto.VoteCapability;
import civitas.crypto.concrete.CryptoFactoryC;
import civitas.crypto.concrete.VoteCapabilityC;

public class VoteCapabilityFromXML {

	public VoteCapabilityC apply(Reader r)
			throws IllegalArgumentException, IOException {
		String s = Util
				.unescapeString(Util.readSimpleTag(r, VoteCapability.OPENING_TAG));
		return new VoteCapabilityC(CryptoFactoryC.stringToBigInt(s));
	}

}
