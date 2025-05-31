package civitas.crypto.oneoflreencryption;

import java.io.PrintWriter;
import java.io.StringWriter;

import civitas.crypto.ciphertext.ElGamalCiphertextToXML;
import civitas.crypto.proof1ofl.ElGamalProof1OfLToXML;
import civitas.util.Use;

public class ElGamal1OfLReencryptionToXML {
	@Use
	ElGamalCiphertextToXML elGamalCiphertextToXML;

	@Use
	ElGamalProof1OfLToXML elGamalProof1OfLToXML;

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
			elGamalProof1OfLToXML.apply(that.proof, s);
		s.print("</elGamal1OfLReencryption>");
	}

}
