package civitas.crypto.petdecommitment;

import java.io.PrintWriter;
import java.io.StringWriter;

import civitas.common.Util;
import civitas.crypto.Constants;
import civitas.crypto.proofdisclog.ElGamalProofDiscLogEquality;
import civitas.crypto.proofdisclog.ElGamalProofDiscLogEqualityToXML;
import civitas.util.Use;

public class PETDecommitmentToXML implements Constants {
	@Use
	ElGamalProofDiscLogEqualityToXML elGamalProofDiscLogEqualityToXML;

	public String apply(PETDecommitmentC that) {
		StringWriter sb = new StringWriter();
		apply(that, new PrintWriter(sb));
		return sb.toString();
	}

	public void apply(PETDecommitmentC that, PrintWriter s) {
		s.print('<');
		s.print(PETDecommitmentOPENING_TAG);
		s.print('>');
		s.print("<d>");
		if (that.di != null)
			Util.escapeString(Util.fromBigInt(that.di), s);
		s.print("</d>");
		s.print("<e>");
		if (that.ei != null)
			Util.escapeString(Util.fromBigInt(that.ei), s);
		s.print("</e>");
		s.print("<prf>");
		elGamalProofDiscLogEqualityToXML
				.apply((ElGamalProofDiscLogEquality) that.proof, s);
		s.print("</prf>");
		s.print("</");
		s.print(PETDecommitmentOPENING_TAG);
		s.print('>');
	}

}
