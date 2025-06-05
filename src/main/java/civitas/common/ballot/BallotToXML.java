package civitas.common.ballot;

import java.io.PrintWriter;

import civitas.common.CommonConstants;
import civitas.common.ballotdesign.CalculatePositionInBallot;
import civitas.common.ballotdesign.ConvertChoiceToString;
import civitas.util.Use;
import lombok.NonNull;

public class BallotToXML implements CommonConstants {

	@Use
	ConvertChoiceToString convertChoiceToString;
	@Use
	CalculatePositionInBallot calculatePositionInBallot;

	public void apply(@NonNull Ballot that, @NonNull PrintWriter sb) {
		sb.print("<" + CondorcetBallotOPENING_TAG + ">");
		sb.print("<k>");
		sb.print(that.k);
		sb.print("</k>");
		sb.print("<matrix>");
		for (int i = 0; i < that.k; i++) {
			for (int j = i + 1; j < that.k; j++) {
				sb.print("<entry>");
				sb.print("<i>");
				sb.print(i);
				sb.print("</i>");
				sb.print("<j>");
				sb.print(j);
				sb.print("</j>");
				sb.print("<choice>");
				sb.print(convertChoiceToString
						.apply(that.matrix[calculatePositionInBallot.apply(i, j, that.k)]));
				sb.print("</choice>");
				sb.print("</entry>");
			}
		}
		sb.print("</matrix>");
		sb.print("</" + CondorcetBallotOPENING_TAG + ">");
	}

}
