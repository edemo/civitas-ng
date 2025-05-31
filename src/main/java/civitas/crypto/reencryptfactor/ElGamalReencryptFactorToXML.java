package civitas.crypto.reencryptfactor;

import java.io.PrintWriter;
import java.io.StringWriter;

import civitas.common.Util;

public class ElGamalReencryptFactorToXML {

	public String apply(ElGamalReencryptFactor that) {
		StringWriter sb = new StringWriter();
		apply(that, new PrintWriter(sb));
		return sb.toString();
	}

	public void apply(ElGamalReencryptFactor that, PrintWriter s) {
		s.print("<r>");
		if (that.r != null)
			Util.escapeString(Util.fromBigInt(that.r), s);
		s.print("</r>");
	}

}
