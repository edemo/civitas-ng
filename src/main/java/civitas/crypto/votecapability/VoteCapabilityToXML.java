package civitas.crypto.votecapability;

import java.io.PrintWriter;

import civitas.common.Util;
import civitas.crypto.Constants;

public class VoteCapabilityToXML implements Constants {

	public void apply(VoteCapability that, PrintWriter s) {
		s.print('<');
		s.print(VoteCapabilityOPENING_TAG);
		s.print('>');
		if (that.m != null) {
			Util.escapeString(Util.fromBigInt(that.m), s);
		}
		s.print("</");
		s.print(VoteCapabilityOPENING_TAG);
		s.print('>');
	}

}
