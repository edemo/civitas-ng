package civitas.common.ballotdesign;

public class CreateBallotDesign {

	public BallotDesign apply(String... candidates) {
		return new BallotDesign(candidates.clone());
	}
}
