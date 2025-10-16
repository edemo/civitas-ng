package civitas.result;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class CalculateWinnerList {
	@Autowired
	TransitiveClosure transitiveClosure;

	@Autowired
	InitialMatrix initialMatrix;

	@Autowired
	ComputeWinners computeWinners;

	@Autowired
	IsFullyIgnored isFullyIgnored;

	public List<List<CandidateResult>> apply(final Integer[][] matrix, final String... candidates) {
		int size = matrix.length;
		CandidatePair[][] initial = initialMatrix.apply(matrix, size);
		transitiveClosure.apply(initial, size);
		Boolean[] ignore = new Boolean[size];
		Arrays.fill(ignore, false);
		List<List<CandidateResult>> winnerList = new ArrayList<>();
		List<CandidateResult> lastWinners = new ArrayList<>();
		while (!isFullyIgnored.apply(ignore)) {
			List<Integer> winners = computeWinners.apply(initial, size, ignore);
			List<CandidateResult> winningCandidates = new ArrayList<>();
			for (Integer winner : winners) {
				ignore[winner] = true;
				winningCandidates.add(
						new CandidateResult(winner, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), 1.0));
				for (CandidateResult lastWinner : lastWinners) {
					Integer i = lastWinner.candidate;
					lastWinner.beatenCandidates.add(winner);
					Integer beat = matrix[i][winner];
					Integer beaten = matrix[winner][i];
					lastWinner.beat.add(beat);
					lastWinner.beaten.add(beaten);

					int beatSize = lastWinner.beat.size();
					lastWinner.strength = (lastWinner.strength * (beatSize - 1) + 1.0 * beat / beaten) / beatSize;
				}
			}
			lastWinners = winningCandidates;
			winnerList.add(winningCandidates);
		}
		return winnerList;
	}
}
