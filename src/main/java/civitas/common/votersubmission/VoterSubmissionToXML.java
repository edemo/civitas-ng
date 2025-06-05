package civitas.common.votersubmission;

import java.io.PrintWriter;

import civitas.common.verifiablevote.VerifiableVote;
import civitas.common.verifiablevote.VerifiableVoteToXML;
import civitas.util.Use;

public class VoterSubmissionToXML {

	@Use
	VerifiableVoteToXML verifiableVoteToXML;

	public void apply(VoterSubmission that, PrintWriter sb) {
		if (sb == null)
			return;
		sb.print("<voterSubmission>");
		sb.print("<voterBlock>");
		sb.print(that.voterBlock);
		sb.print("</voterBlock>");
		if (that.votes != null) {
			sb.print("<size>");
			sb.print(that.votes.length);
			sb.print("</size>");
		} else {
			sb.print("<size>0</size>");
		}
		sb.print("<votes>");
		try {
			for (VerifiableVote vote : that.votes) {
				verifiableVoteToXML.apply(vote, sb);
			}
		} catch (NullPointerException imposs) {
		} catch (ArrayIndexOutOfBoundsException imposs) {
		}
		sb.print("</votes>");
		sb.print("</voterSubmission>");
	}

}
