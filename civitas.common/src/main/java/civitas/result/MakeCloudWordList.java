package civitas.result;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;

@Controller
public class MakeCloudWordList {
	public List<CandidatePair<String, Long>> apply(
			final String[] candidates, final List<List<CandidateResult>> winnerList) {
		List<CandidatePair<String, Long>> cloudWords = new ArrayList<>();
		for (List<CandidateResult> step : winnerList) {
			for (CandidateResult result : step) {
				String name = candidates[result.candidate];
				Long strength = result.strength.longValue();
				CandidatePair<String, Long> e = new CandidatePair<>(name, strength);
				cloudWords.add(e);
			}
		}
		return cloudWords;
	}
}
