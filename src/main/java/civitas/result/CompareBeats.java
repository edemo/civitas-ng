package civitas.result;

import org.springframework.stereotype.Service;

@Service
public class CompareBeats {
	Integer apply(Pair<Integer, Integer> b1, Pair<Integer, Integer> b2) {
		if (b1.car > b2.car)
			return 1;
		if (b1.car < b2.car)
			return -1;
		if (b1.cdr < b2.cdr)
			return 1;
		if (b1.cdr > b2.cdr)
			return -1;
		return 0;
	}
}
