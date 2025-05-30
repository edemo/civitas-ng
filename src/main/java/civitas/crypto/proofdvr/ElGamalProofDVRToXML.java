package civitas.crypto.proofdvr;

import java.io.PrintWriter;
import java.io.StringWriter;

import civitas.common.Util;
import civitas.crypto.ciphertext.ElGamalCiphertextToXML;
import civitas.util.Use;

public class ElGamalProofDVRToXML {
	@Use
	ElGamalCiphertextToXML elGamalCiphertextToXML;

	public String apply(ElGamalProofDVRC that) {
		StringWriter sb = new StringWriter();
		apply(that, new PrintWriter(sb));
		return sb.toString();
	}

	public void apply(ElGamalProofDVRC that, PrintWriter s) {
		s.print("<elGamalProofDVR>");
		elGamalCiphertextToXML.apply(that.e, s);
		elGamalCiphertextToXML.apply(that.eprime, s);
		s.print("<c>");
		Util.escapeString(Util.fromBigInt(that.c), s);
		s.print("</c>");
		s.print("<w>");
		Util.escapeString(Util.fromBigInt(that.w), s);
		s.print("</w>");
		s.print("<r>");
		Util.escapeString(Util.fromBigInt(that.r), s);
		s.print("</r>");
		s.print("<u>");
		Util.escapeString(Util.fromBigInt(that.u), s);
		s.print("</u>");
		s.print("</elGamalProofDVR>");
	}

}
