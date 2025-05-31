package civitas.crypto.rsaprivatekey;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Base64;

import civitas.common.Util;
import civitas.crypto.Constants;

public class PrivateKeyToXML implements Constants {

	public String apply(PrivateKey that) {
		StringWriter sb = new StringWriter();
		apply(that, new PrintWriter(sb));
		return sb.toString();
	}

	public void apply(PrivateKey that, PrintWriter s) {
		s.print('<');
		s.print(PrivateKeyOPENING_TAG);
		s.print('>');
		byte[] bs = that.k.getEncoded();
		Util.escapeString(Base64.getEncoder().encodeToString(bs), s);
		s.print("</");
		s.print(PrivateKeyOPENING_TAG);
		s.print('>');
	}

}
