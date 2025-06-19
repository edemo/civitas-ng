package civitas.result;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class CalculateStrengths {
	public void apply(String[] candidates,
			List<List<CandidateResult>> winnerList) {
		int noneOfABove = candidates.length - 1;
		Double base = null;
		for (int i = winnerList.size() - 1; i >= 0; i--) {
			List<CandidateResult> step = winnerList.get(i);
			if (base != null) {
				double avg = 0.0;
				for (CandidateResult result : step) {
					avg += result.strength;
					result.strength *= base;
				}
				avg /= step.size();
				base = base * avg;
			} else {
				for (CandidateResult result : step) {
					if (result.candidate == noneOfABove)
						base = 10.0;
					result.strength = 0.0;
				}
			}
		}
	}

}
