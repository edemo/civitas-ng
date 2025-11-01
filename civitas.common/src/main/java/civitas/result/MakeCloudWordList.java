package civitas.result;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;

@Controller
public class MakeCloudWordList {
	public List<NameAndStrength> apply(final String[] candidates, final List<List<CandidateResult>> winnerList) {
		var cloudWords = new ArrayList<NameAndStrength>();
		for (List<CandidateResult> step : winnerList) {
			for (CandidateResult result : step) {
				String name = candidates[result.candidate];
				Long strength = result.strength.longValue();
				var e = new NameAndStrength(name, strength);
				cloudWords.add(e);
			}
		}
		return cloudWords;
	}
}
