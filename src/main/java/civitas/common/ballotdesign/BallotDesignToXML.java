package civitas.common.ballotdesign;

import java.io.PrintWriter;

import civitas.common.CommonConstants;
import civitas.common.Util;

public class BallotDesignToXML implements CommonConstants {

	public void apply(BallotDesign that, PrintWriter sb) {
		sb.print("<" + BallotDesignOPENING_TAG + ">");
		sb.print("<size>");
		sb.print(that.candidates.length);
		sb.print("</size>");
		sb.print("<candidates>");
		for (String candidate : that.candidates) {
			sb.print("<candidate>");
			Util.escapeString(candidate, sb);
			sb.print("</candidate>");
		}
		sb.print("</candidates>");
		sb.print("</" + BallotDesignOPENING_TAG + ">");
	}

}
