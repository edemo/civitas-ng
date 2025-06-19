package civitas.result;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Min {

	@Autowired
	CompareBeats compareBeats;

	Pair<Integer, Integer> apply(Pair<Integer, Integer> xr,
			Pair<Integer, Integer> yr) {
		if (compareBeats.apply(xr, yr) > 0)
			return yr;
		return xr;

	}
}
