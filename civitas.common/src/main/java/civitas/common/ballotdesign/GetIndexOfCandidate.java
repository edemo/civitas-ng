package civitas.common.ballotdesign;

public class GetIndexOfCandidate {
	public int apply(BallotDesign that, String cand) {
		for (int i = 0; i < that.candidates.length; i++) {
			String cD = that.candidates[i];
			if (cD == cand || cD.equalsIgnoreCase(cand)) {
				return i;
			}
		}
		return -1;
	}

}
