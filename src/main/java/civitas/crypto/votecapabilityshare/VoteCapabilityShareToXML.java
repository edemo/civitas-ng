package civitas.crypto.votecapabilityshare;

import java.io.PrintWriter;

import civitas.common.Util;
import civitas.crypto.Constants;

public class VoteCapabilityShareToXML implements Constants {

	public void apply(VoteCapabilityShare that, PrintWriter s) {
		s.print('<');
		s.print(VoteCapabilityShareOPENING_TAG);
		s.print('>');
		if (that.m != null) {
			Util.escapeString(Util.fromBigInt(that.m), s);
		}
		s.print("</");
		s.print(VoteCapabilityShareOPENING_TAG);
		s.print('>');
	}

}
