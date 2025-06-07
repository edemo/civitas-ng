package civitas.result;

import civitas.util.Use;

public class Min {

	@Use
	CompareBeats compareBeats;

	Pair<Integer, Integer> apply(Pair<Integer, Integer> xr,
			Pair<Integer, Integer> yr) {
		if (compareBeats.apply(xr, yr) > 0)
			return yr;
		return xr;

	}
}
