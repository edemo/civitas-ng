package civitas.crypto.proof1ofl;

import java.io.PrintWriter;
import java.io.StringWriter;

import civitas.common.Util;

public class ElGamalProof1OfLToXML {

	public String apply(ElGamalProof1OfL that) {
		StringWriter sb = new StringWriter();
		apply(that, new PrintWriter(sb));
		return sb.toString();
	}

	public void apply(ElGamalProof1OfL that, PrintWriter s) {
		s.print("<elGamalProof1OfL>");
		s.print("<size>");
		s.print(that.L);
		s.print("</size>");
		for (int i = 0; i < that.L; i++) {
			s.print("<dv>");
			if (that.dvs[i] != null)
				Util.escapeString(Util.fromBigInt(that.dvs[i]), s);
			s.print("</dv>");
		}
		for (int i = 0; i < that.L; i++) {
			s.print("<rv>");
			if (that.rvs[i] != null)
				Util.escapeString(Util.fromBigInt(that.rvs[i]), s);
			s.print("</rv>");
		}
		s.print("</elGamalProof1OfL>");
	}

}
