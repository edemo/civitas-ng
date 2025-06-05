package civitas.common.verifiablevote;

import java.io.PrintWriter;

import civitas.common.CommonConstants;
import civitas.common.Util;
import civitas.crypto.ciphertext.ElGamalCiphertextToXML;
import civitas.crypto.oneoflreencryption.ElGamal1OfLReencryptionToXML;
import civitas.crypto.proofvote.ProofVoteToXML;
import civitas.util.Use;

public class VerifiableVoteToXML implements CommonConstants {
	@Use
	ElGamal1OfLReencryptionToXML elGamal1OfLReencryptionToXML;
	@Use
	ElGamalCiphertextToXML elGamalCiphertextToXML;
	@Use
	ProofVoteToXML proofVoteToXML;

	public void apply(VerifiableVote that, PrintWriter sb) {
		if (sb == null)
			return;
		sb.print("<");
		sb.print(VerifiableVoteOPENING_TAG);
		sb.print(">");
		sb.print("<context>");
		Util.escapeString(that.context, sb);
		sb.print("</context>");
		sb.print("<encChoice>");
		if (that.encChoice != null) {
			elGamal1OfLReencryptionToXML.apply(that.encChoice, sb);
		}
		sb.print("</encChoice>");
		sb.print("<encCapability>");
		if (that.encCapability != null) {
			elGamalCiphertextToXML.apply(that.encCapability, sb);
		}
		sb.print("</encCapability>");
		sb.print("<proof>");
		if (that.proofVote != null) {
			proofVoteToXML.apply(that.proofVote, sb);
		}
		sb.print("</proof>");

		sb.print("</" + VerifiableVoteOPENING_TAG + ">");
	}

}
