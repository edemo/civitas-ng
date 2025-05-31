package civitas.crypto.sharedkey;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Base64;

import civitas.common.Util;
import civitas.crypto.Constants;

public class SharedKeyToXML implements Constants {

	public String apply(SharedKeyC that) {
		StringWriter sb = new StringWriter();
		apply(that, new PrintWriter(sb));
		return sb.toString();
	}

	public void apply(SharedKeyC that, PrintWriter s) {
		s.print('<');
		s.print(SharedKeyOPENING_TAG);
		s.print('>');
		s.print("<n>");
		s.print(that.name);
		s.print("</n>");
		s.print("<k>");
		byte[] bs = that.k.getEncoded();
		Util.escapeString(Base64.getEncoder().encodeToString(bs), s);
		s.print("</k>");
		s.print("</");
		s.print(SharedKeyOPENING_TAG);
		s.print('>');
	}

}
