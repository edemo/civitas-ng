package civitas.common.ballotdesign;

import org.springframework.stereotype.Service;

import civitas.common.CommonConstants;

@Service
public class GetNthContext implements CommonConstants {

	public String apply(BallotDesign that, int n) {
		int c = 0;
		int k = that.candidates.length;
		for (int i = 0; i < k; i++) {
			for (int j = i + 1; j < k; j++) {
				if (n == c) {
					return KIND + i + ":" + j;
				}
				c++;
			}
		}
		return null;
	}

}
