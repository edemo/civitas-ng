package civitas.common.ballotdesign;

public class CreateBallotDesign {

	public BallotDesign apply(String[] candidates) {
		String[] cs;
		if (candidates != null) {
			cs = candidates.clone();
		} else {
			cs = new String[0];
		}
		return new BallotDesign(cs);
	}

}
