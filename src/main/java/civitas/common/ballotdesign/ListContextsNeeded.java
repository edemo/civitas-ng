package civitas.common.ballotdesign;

import java.util.ArrayList;
import java.util.List;

import civitas.common.CommonConstants;

public class ListContextsNeeded implements CommonConstants {

	/**
	 * Returns a list of the contexts that will be used in a vote submission. The
	 * domain of the map capabilities given to the decompose method must contain
	 * these strings.
	 */
	public List<String> apply(BallotDesign that, String baseContext)
			throws IllegalArgumentException {
		List<String> l = new ArrayList<>();
		apply(that, l, baseContext);
		return l;
	}

	public void apply(BallotDesign that, List<String> l, String context)
			throws IllegalArgumentException {
		if (l == null)
			return;
		int k = that.candidates.length;
		for (int i = 0; i < k; i++) {
			for (int j = i + 1; j < k; j++) {
				try {
					l.add((context == null ? "" : context) + KIND + i + ":" + j);
				} catch (ClassCastException unlikely) {
				}
			}
		}
	}

}
