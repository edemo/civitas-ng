package civitas.crypto.decriptionshare;

import java.io.PrintWriter;
import java.io.StringWriter;

import civitas.common.Util;
import civitas.crypto.Constants;

public class ElGamalDecryptionShareToXML {

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
			that.proof.toXML(s);
		s.print("</");
		s.print(Constants.ElGamalDecryptionShareOPENING_TAG);
		s.print('>');
	}

}
