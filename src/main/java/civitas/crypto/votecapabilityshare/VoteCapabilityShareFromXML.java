package civitas.crypto.votecapabilityshare;

import java.io.IOException;
import java.io.Reader;

import civitas.common.Util;
import civitas.crypto.Constants;

public class VoteCapabilityShareFromXML implements Constants {

	public VoteCapabilityShare apply(Reader r)
			throws IllegalArgumentException, IOException {
		String s = Util
				.unescapeString(Util.readSimpleTag(r, VoteCapabilityShareOPENING_TAG));
		return new VoteCapabilityShare(Util.asBigint(s));
	}

}
