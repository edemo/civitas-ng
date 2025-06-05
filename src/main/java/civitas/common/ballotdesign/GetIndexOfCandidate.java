package civitas.common.ballotdesign;

public class GetIndexOfCandidate {
	/**
	 * Return the index of the candidate cand, -1 if cand is not a candidate.
	 */
	public int indexOfCandidate(BallotDesign that, String cand) {
		if (that.candidates == null)
			return -1;
		for (int i = 0; i < that.candidates.length; i++) {
			try {
				String cD = that.candidates[i];
				if (cD == cand || (cD != null && cD.equalsIgnoreCase(cand))) {
					return i;
				}
			} catch (ArrayIndexOutOfBoundsException imposs) {
			}
		}
		return -1;
	}

}
