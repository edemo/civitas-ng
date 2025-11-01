package civitas.result;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;

@Controller
public class FormatResult {
	public String apply(final String[] candidates, final List<List<CandidateResult>> winnerList) {
		var winResult = new ArrayList<String>();
		int stagenum = 0;
		for (List<CandidateResult> stage : winnerList) {
			stagenum++;
			var winners = new ArrayList<String>();
			for (CandidateResult winner : stage) {
				var beats = new ArrayList<String>();
				for (int i = 0; i < winner.beatenCandidates.size(); i++) {
					Integer beat = winner.beat.get(i);
					Integer beaten = winner.beaten.get(i);
					String b = String.format("%.2f", 1.0 * beat / beaten);
					beats.add("beats " + candidates[winner.beatenCandidates.get(i)] + " by " + beat + ":" + beaten
							+ " (" + b + ")");
				}
				String strength = String.format("%.2f", winner.strength);
				winners.add(candidates[winner.candidate] + " (" + strength + ") (" + String.join(", ", beats) + ") ");
			}
			winResult.add(stagenum + ":\n\t" + String.join("\n\t", winners));
		}
		return String.join("\n", winResult);
	}
}
