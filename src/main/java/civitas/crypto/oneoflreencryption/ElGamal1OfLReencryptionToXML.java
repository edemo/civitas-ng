package civitas.crypto.oneoflreencryption;

import java.io.PrintWriter;
import java.io.StringWriter;

import civitas.crypto.ciphertext.ElGamalCiphertextToXML;
import civitas.util.Use;

public class ElGamal1OfLReencryptionToXML {
	@Use
	ElGamalCiphertextToXML elGamalCiphertextToXML;

	public String apply(ElGamal1OfLReencryption that) {
		StringWriter sb = new StringWriter();
		apply(that, new PrintWriter(sb));
		return sb.toString();
	}

	public void apply(ElGamal1OfLReencryption that, PrintWriter s) {
		s.print("<elGamal1OfLReencryption>");
		if (that.m != null)
			elGamalCiphertextToXML.apply(that.m, s);
		if (that.proof != null)
			that.proof.toXML(s);
		s.print("</elGamal1OfLReencryption>");
	}

}
