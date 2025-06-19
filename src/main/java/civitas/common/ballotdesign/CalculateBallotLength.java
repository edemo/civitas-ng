package civitas.common.ballotdesign;

import org.springframework.stereotype.Service;

@Service
public class CalculateBallotLength {

	public int apply(Integer k) {
		return (int) ((k - 1) * (k / 2.0));
	}

}
