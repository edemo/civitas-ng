package civitas.result;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class ComputeWinners {

	@Autowired
	CompareBeats compareBeats;

	List<Integer> apply(final CandidatePair[][] m, final Integer n, final Boolean[] ignore) {
		var winners = new ArrayList<Integer>();
		for (int i = 0; i < n; i++) {
			if (!ignore[i] && didWin(m, n, ignore, i)) {
				winners.add(i);
			}
		}

		return winners;
	}

	private Boolean didWin(final CandidatePair[][] m, final Integer n, final Boolean[] ignore, final int i) {
		for (int j = 0; j < n; j++) {
			if (!ignore[j] && compareBeats.apply(m[j][i], m[i][j]) > 0) {
				return false;
			}
		}
		return true;
	}
}
