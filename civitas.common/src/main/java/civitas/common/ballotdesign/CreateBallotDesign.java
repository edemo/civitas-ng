package civitas.common.ballotdesign;

public class CreateBallotDesign {

	public BallotDesign apply(final String... candidates) {
		return new BallotDesign(candidates.clone());
	}
}
