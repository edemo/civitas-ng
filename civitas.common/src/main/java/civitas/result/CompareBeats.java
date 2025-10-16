package civitas.result;

import org.springframework.stereotype.Controller;

@Controller
public class CompareBeats {
	Integer apply(final CandidatePair b1, final CandidatePair b2) {
		if (b1.car() > b2.car()) {
			return 1;
		}
		if (b1.car() < b2.car()) {
			return -1;
		}
		if (b1.cdr() < b2.cdr()) {
			return 1;
		}
		if (b1.cdr() > b2.cdr()) {
			return -1;
		}
		return 0;
	}
}
