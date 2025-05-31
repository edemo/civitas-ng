package civitas.crypto.decriptionshare;

import java.io.PrintWriter;
import java.io.StringWriter;

import civitas.common.Util;
import civitas.crypto.Constants;
import civitas.crypto.proofdisclog.ElGamalProofDiscLogEqualityToXML;
import civitas.util.Use;

public class ElGamalDecryptionShareToXML {
	@Use
	ElGamalProofDiscLogEqualityToXML elGamalProofDiscLogEqualityToXML;

	public String apply(ElGamalDecryptionShare that) {
		StringWriter sb = new StringWriter();
		apply(that, new PrintWriter(sb));
		return sb.toString();
	}

	public void apply(ElGamalDecryptionShare that, PrintWriter s) {
		s.print('<');
		s.print(Constants.ElGamalDecryptionShareOPENING_TAG);
		s.print('>');
		s.print("<ai>");
		if (that.ai != null)
			Util.escapeString(Util.fromBigInt(that.ai), s);
		s.print("</ai>");
		if (that.proof != null)
			elGamalProofDiscLogEqualityToXML.apply(that.proof, s);
		s.print("</");
		s.print(Constants.ElGamalDecryptionShareOPENING_TAG);
		s.print('>');
	}

}
