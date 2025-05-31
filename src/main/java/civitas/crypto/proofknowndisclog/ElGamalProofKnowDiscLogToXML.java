package civitas.crypto.proofknowndisclog;

import java.io.PrintWriter;
import java.io.StringWriter;

import civitas.common.Util;

public class ElGamalProofKnowDiscLogToXML {

	public String apply(ElGamalProofKnowDiscLog that) {
		StringWriter sb = new StringWriter();
		apply(that, new PrintWriter(sb));
		return sb.toString();
	}

	public void apply(ElGamalProofKnowDiscLog that, PrintWriter s) {
		s.print("<elGamalProofKnowDiscLog>");

		s.print("<a>");
		if (that.a != null)
			Util.escapeString(Util.fromBigInt(that.a), s);
		s.print("</a>");
		s.print("<c>");
		if (that.c != null)
			Util.escapeString(Util.fromBigInt(that.c), s);
		s.print("</c>");
		s.print("<r>");
		if (that.r != null)
			Util.escapeString(Util.fromBigInt(that.r), s);
		s.print("</r>");
		s.print("<v>");
		if (that.v != null)
			Util.escapeString(Util.fromBigInt(that.v), s);
		s.print("</v>");

		s.print("</elGamalProofKnowDiscLog>");
	}

}
