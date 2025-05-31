package civitas.crypto.parameters;

import java.io.PrintWriter;
import java.io.StringWriter;

import civitas.common.Util;

public class ElGamalParametersToXML {
	public String apply(ElGamalParameters that) {
		StringWriter sb = new StringWriter();
		apply(that, new PrintWriter(sb));
		return sb.toString();
	}

	public void apply(ElGamalParameters that, PrintWriter s) {
		s.print("<elGamalParameters>");

		s.print("<p>");
		Util.escapeString(Util.fromBigInt(that.p), s);
		s.print("</p>");
		s.print("<q>");
		Util.escapeString(Util.fromBigInt(that.q), s);
		s.print("</q>");
		s.print("<g>");
		Util.escapeString(Util.fromBigInt(that.g), s);
		s.print("</g>");

		s.print("</elGamalParameters>");
	}

}
