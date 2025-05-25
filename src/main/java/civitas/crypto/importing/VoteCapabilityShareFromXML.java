package civitas.crypto.importing;

import java.io.IOException;
import java.io.Reader;

import civitas.common.Util;
import civitas.crypto.VoteCapabilityShare;
import civitas.crypto.concrete.CryptoFactoryC;
import civitas.crypto.concrete.VoteCapabilityShareC;

public class VoteCapabilityShareFromXML {

	public VoteCapabilityShareC apply(Reader r)
			throws IllegalArgumentException, IOException {
		String s = Util
				.unescapeString(Util.readSimpleTag(r, VoteCapabilityShare.OPENING_TAG));
		return new VoteCapabilityShareC(CryptoFactoryC.stringToBigInt(s));
	}

}
