package civitas.result;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class Max {

	@Autowired
	CompareBeats compareBeats;

	CandidatePair apply(final CandidatePair xr, final CandidatePair yr) {
		if (compareBeats.apply(xr, yr) > 0) {
			return xr;
		}
		return yr;
	}
}
