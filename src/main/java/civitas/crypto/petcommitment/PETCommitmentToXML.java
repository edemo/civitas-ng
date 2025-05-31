package civitas.crypto.petcommitment;

import java.io.PrintWriter;
import java.io.StringWriter;

import civitas.common.Util;
import civitas.crypto.Constants;

public class PETCommitmentToXML implements Constants {
	public String apply(PETCommitment that) {
		StringWriter sb = new StringWriter();
		apply(that, new PrintWriter(sb));
		return sb.toString();
	}

	public void apply(PETCommitment that, PrintWriter s) {
		s.print('<');
		s.print(PETCommitmentOPENING_TAG);
		s.print('>');

		if (that.hash != null)
			Util.escapeString(Util.fromBigInt(that.hash), s);

		s.print("</");
		s.print(PETCommitmentOPENING_TAG);
		s.print('>');
	}

}
