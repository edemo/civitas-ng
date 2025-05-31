package civitas.crypto.proofdisclog;

import java.io.PrintWriter;
import java.io.StringWriter;

import civitas.common.Util;

public class ElGamalProofDiscLogEqualityToXML {
	public String apply(ElGamalProofDiscLogEquality that) {
		StringWriter sb = new StringWriter();
		apply(that, new PrintWriter(sb));
		return sb.toString();
	}

	public void apply(ElGamalProofDiscLogEquality that, PrintWriter s) {
		s.print("<egPrfKnwDscLog>");

		s.print("<g1>");
		if (that.g1 != null)
			Util.escapeString(Util.fromBigInt(that.g1), s);
		s.print("</g1>");
		s.print("<g2>");
		if (that.g2 != null)
			Util.escapeString(Util.fromBigInt(that.g2), s);
		s.print("</g2>");
		s.print("<v>");
		if (that.v != null)
			Util.escapeString(Util.fromBigInt(that.v), s);
		s.print("</v>");
		s.print("<w>");
		if (that.w != null)
			Util.escapeString(Util.fromBigInt(that.w), s);
		s.print("</w>");
		s.print("<a>");
		if (that.a != null)
			Util.escapeString(Util.fromBigInt(that.a), s);
		s.print("</a>");
		s.print("<b>");
		if (that.b != null)
			Util.escapeString(Util.fromBigInt(that.b), s);
		s.print("</b>");
		s.print("<c>");
		if (that.c != null)
			Util.escapeString(Util.fromBigInt(that.c), s);
		s.print("</c>");
		s.print("<r>");
		if (that.r != null)
			Util.escapeString(Util.fromBigInt(that.r), s);
		s.print("</r>");

		s.print("</egPrfKnwDscLog>");
	}

}
