package civitas.crypto.rsapublickey;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Base64;

import civitas.common.Util;
import civitas.crypto.Constants;

public class PublicKeyToXML implements Constants {

	public String apply(PublicKey that) {
		StringWriter sb = new StringWriter();
		apply(that, new PrintWriter(sb));
		return sb.toString();
	}

	public void apply(PublicKey that, PrintWriter s) {
		s.print('<');
		s.print(PublicKeyOPENING_TAG);
		s.print('>');
		s.print("<name>");
		s.print(that.name);
		s.print("</name>");
		s.print("<key>");
		byte[] bs = that.k.getEncoded();
		Util.escapeString(Base64.getEncoder().encodeToString(bs), s);
		s.print("</key>");
		s.print("</");
		s.print(PublicKeyOPENING_TAG);
		s.print('>');
	}

}
