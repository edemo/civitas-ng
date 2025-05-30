package civitas.common;

import java.io.PrintWriter;

import civitas.crypto.ciphertext.ElGamalCiphertextToXML;
import civitas.crypto.oneoflreencryption.ElGamal1OfLReencryptionC;
import civitas.crypto.oneoflreencryption.ElGamal1OfLReencryptionToXML;
import civitas.util.Use;

public class VerifiableVoteToXML {
	@Use
	ElGamal1OfLReencryptionToXML elGamal1OfLReencryptionToXML;
	@Use
	ElGamalCiphertextToXML elGamalCiphertextToXML;

	public void apply(VerifiableVote that, PrintWriter sb) {
		if (sb == null)
			return;
		sb.print("<");
		sb.print(VerifiableVote.OPENING_TAG);
		sb.print(">");
		sb.print("<context>");
		Util.escapeString(that.context, sb);
		sb.print("</context>");
		sb.print("<encChoice>");
		if (that.encChoice != null) {
			elGamal1OfLReencryptionToXML
					.apply((ElGamal1OfLReencryptionC) that.encChoice, sb);
		}
		sb.print("</encChoice>");
		sb.print("<encCapability>");
		if (that.encCapability != null) {
			elGamalCiphertextToXML.apply(that.encCapability, sb);
		}
		sb.print("</encCapability>");
		sb.print("<proof>");
		if (that.proofVote != null) {
			that.proofVote.toXML(sb);
		}
		sb.print("</proof>");

		sb.print("</" + VerifiableVote.OPENING_TAG + ">");
	}

}
