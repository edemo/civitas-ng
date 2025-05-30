package civitas.crypto.keyshare;

import java.io.PrintWriter;
import java.io.StringWriter;

import civitas.crypto.publickey.ElGamalPublicKeyToXML;
import civitas.util.Use;

public class ElGamalKeyShareToXML {
	@Use
	ElGamalPublicKeyToXML elGamalPublicKeyToXML;

	public String apply(ElGamalKeyShare that) {
		StringWriter sb = new StringWriter();
		apply(that, new PrintWriter(sb));
		return sb.toString();
	}

	public void apply(ElGamalKeyShare that, PrintWriter s) {
		s.print("<elGamalKeyShare>");
		s.print("<pubKey>");
		if (that.pubKey != null)
			elGamalPublicKeyToXML.apply(that.pubKey, s);
		s.print("</pubKey>");
		s.print("<proof>");
		if (that.proof != null)
			that.proof.toXML(s);
		s.print("</proof>");
		s.print("</elGamalKeyShare>");
	}

}
