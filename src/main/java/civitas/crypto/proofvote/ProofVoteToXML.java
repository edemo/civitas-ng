package civitas.crypto.proofvote;

import java.io.PrintWriter;
import java.io.StringWriter;

import civitas.common.Util;

public class ProofVoteToXML {
	public String apply(ProofVoteC that) {
		StringWriter sb = new StringWriter();
		apply(that, new PrintWriter(sb));
		return sb.toString();
	}

	public void apply(ProofVoteC that, PrintWriter s) {
		s.print("<elGamalProofVote>");

		s.print("<c>");
		if (that.c != null)
			Util.escapeString(Util.fromBigInt(that.c), s);
		s.print("</c>");
		s.print("<s1>");
		if (that.s1 != null)
			Util.escapeString(Util.fromBigInt(that.s1), s);
		s.print("</s1>");
		s.print("<s2>");
		if (that.s2 != null)
			Util.escapeString(Util.fromBigInt(that.s2), s);
		s.print("</s2>");

		s.print("</elGamalProofVote>");
	}

}
