package civitas.result;

import java.util.ArrayList;
import java.util.List;

public class FormatResult {
	public String apply(String[] candidates,
			List<List<CandidateResult>> winnerList) {
		List<String> winResult = new ArrayList<>();
		int stagenum = 0;
		for (List<CandidateResult> stage : winnerList) {
			stagenum++;
			List<String> winners = new ArrayList<String>();
			for (CandidateResult winner : stage) {
				List<String> beats = new ArrayList<String>();
				for (int i = 0; i < winner.beatenCandidates.size(); i++) {
					Integer beat = winner.beat.get(i);
					Integer beaten = winner.beaten.get(i);
					String b = String.format("%.2f", 1.0 * beat / beaten);
					beats.add("beats " + candidates[winner.beatenCandidates.get(i)]
							+ " by " + beat + ":" + beaten + " (" + b + ")");
				}
				String strength = String.format("%.2f", winner.strength);
				winners.add(candidates[winner.candidate] + " (" + strength + ") ("
						+ String.join(", ", beats) + ") ");
			}
			winResult.add("" + stagenum + ":\n\t" + String.join("\n\t", winners));
		}
		return String.join("\n", winResult);
	}

}
