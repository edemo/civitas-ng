package civitas.common.ballotdesign;

import java.util.ArrayList;
import java.util.List;

import civitas.common.CommonConstants;

public class ListContextsNeeded implements CommonConstants {

	public List<String> apply(final BallotDesign that, final String baseContext) {
		var l = new ArrayList<String>();
		apply(that, l, baseContext);
		return l;
	}

	private void apply(final BallotDesign that, final List<String> l, final String context) {
		int k = that.candidates.length;
		for (int i = 0; i < k; i++) {
			for (int j = i + 1; j < k; j++) {
				l.add(context + KIND + i + ":" + j);
			}
		}
	}
}
