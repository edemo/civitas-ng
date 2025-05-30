package civitas.crypto.vote;

import java.io.IOException;
import java.io.Reader;

import civitas.common.Util;
import civitas.crypto.votecapabilityshare.VoteCapabilityShare;
import civitas.crypto.votecapabilityshare.VoteCapabilityShareC;

public class VoteCapabilityShareFromXML {

	public VoteCapabilityShareC apply(Reader r)
			throws IllegalArgumentException, IOException {
		String s = Util
				.unescapeString(Util.readSimpleTag(r, VoteCapabilityShare.OPENING_TAG));
		return new VoteCapabilityShareC(Util.asBigint(s));
	}

}
