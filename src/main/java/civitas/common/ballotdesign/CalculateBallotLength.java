package civitas.common.ballotdesign;

public class CalculateBallotLength {

	public int apply(int k) {
		return (int) ((k - 1) * (k / 2.0));
	}

}
