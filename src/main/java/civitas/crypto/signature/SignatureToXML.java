package civitas.crypto.signature;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Base64;

import civitas.common.Util;
import civitas.crypto.Constants;

public class SignatureToXML implements Constants {
	public String apply(Signature that) {
		StringWriter sb = new StringWriter();
		apply(that, new PrintWriter(sb));
		return sb.toString();
	}

	public void apply(Signature that, PrintWriter s) {
		s.print('<');
		s.print(SignatureOPENING_TAG);
		s.print('>');
		Util.escapeString(Base64.getEncoder().encodeToString(that.signature), s);
		s.print("</");
		s.print(SignatureOPENING_TAG);
		s.print('>');
	}

}
