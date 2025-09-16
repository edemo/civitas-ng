package civitas.result;

import org.springframework.stereotype.Controller;

@Controller
public class InitialMatrix {

	CandidatePair<Integer, Integer>[][] apply(Integer[][] m, Integer n) {
		@SuppressWarnings("unchecked")
		CandidatePair<Integer, Integer>[][] r = new CandidatePair[n][n];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				Integer x = m[i][j];
				Integer y = m[j][i];
				if (x == y) {
					r[i][j] = new CandidatePair<>(0, 0);
				} else if (x > y) {
					r[i][j] = new CandidatePair<>(x, y);
					r[j][i] = new CandidatePair<>(0, 0);
				} else {
					r[i][j] = new CandidatePair<>(0, 0);
					r[j][i] = new CandidatePair<>(y, x);
				}
			}
		}
		return r;
	}

}
