package civitas.result;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class MakeCloudWordList {
	public List<Pair<String, Long>> apply(String[] candidates,
			List<List<CandidateResult>> winnerList) {
		List<Pair<String, Long>> cloudWords = new ArrayList<>();
		for (List<CandidateResult> step : winnerList) {
			for (CandidateResult result : step) {
				String name = candidates[result.candidate];
				Long strength = result.strength.longValue();
				Pair<String, Long> e = new Pair<>(name, strength);
				cloudWords.add(e);
			}
		}
		return cloudWords;
	}

}
