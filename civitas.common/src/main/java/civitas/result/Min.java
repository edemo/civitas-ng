package civitas.result;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class Min {

	@Autowired
	CompareBeats compareBeats;

	CandidatePair<Integer, Integer> apply(CandidatePair<Integer, Integer> xr, CandidatePair<Integer, Integer> yr) {
		if (compareBeats.apply(xr, yr) > 0) {
			return yr;
		}
		return xr;
	}
}
