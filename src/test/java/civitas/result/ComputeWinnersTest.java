package civitas.result;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import civitas.common.TestBase;
import civitas.util.Use;

class ComputeWinnersTest extends TestBase implements ResultTestData {

	@Use
	ComputeWinners computeWinners;

	@Use
	InitialMatrix initialMatrix;

	@Use
	TransitiveClosure transitiveClosure;

	@Use
	MakeCloudWordList makeCloudWordList;
	@Use
	CalculateWinnerList calculateWinnerList;
	@Use
	CalculateStrengths calculateStrengths;
	@Use
	FormatResult formatResult;

	@Test
	void test() {

		List<List<CandidateResult>> winnerList = calculateWinnerList.apply(matrix,
				candidates);

		calculateStrengths.apply(candidates, winnerList);

		List<Pair<String, Long>> cloudWords = makeCloudWordList.apply(candidates,
				winnerList);

		List<String> cws = new ArrayList<>();
		cloudWords.forEach(x -> cws.add(x.cdr + "," + x.car + ",#ffffff"));
		assertEquals(CLOUDWORDS_CSV, String.join("\n", cws));
		String report = formatResult.apply(candidates, winnerList);
		assertEquals(REPORT, report);
	}

}
