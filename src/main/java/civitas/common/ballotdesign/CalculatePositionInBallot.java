package civitas.common.ballotdesign;

public class CalculatePositionInBallot {

	public int apply(int i, int j, int k) {
		if (i == 0)
			return j - 1;
		return (j - i) + apply(i - 1, k - 1, k);
	}

}
