package civitas.result;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ComputeWinners {

	@Autowired
	CompareBeats compareBeats;

	List<Integer> apply(Pair<Integer, Integer>[][] m, Integer n,
			Boolean[] ignore) {
		List<Integer> winners = new ArrayList<>();
		for (int i = 0; i < n; i++)
			if (!ignore[i]) {
				if (didWin(m, n, ignore, i))
					winners.add(i);
			}
		return winners;
	}

	private Boolean didWin(Pair<Integer, Integer>[][] m, Integer n,
			Boolean[] ignore, int i) {
		for (int j = 0; j < n; j++) {
			if (!ignore[j]) {
				if (compareBeats.apply(m[j][i], m[i][j]) > 0)
					return false;
			}
		}
		return true;
	}
}
