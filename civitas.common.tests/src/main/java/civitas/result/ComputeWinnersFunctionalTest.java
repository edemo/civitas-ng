package civitas.result;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import civitas.AppTestConfig;
import civitas.common.RandomAwareTestBase;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = AppTestConfig.class)
@Tag("functional")
class ComputeWinnersFunctionalTest extends RandomAwareTestBase
		implements ResultTestData {

	@Autowired
	ComputeWinners computeWinners;

	@Autowired
	InitialMatrix initialMatrix;

	@Autowired
	TransitiveClosure transitiveClosure;

	@Autowired
	MakeCloudWordList makeCloudWordList;
	@Autowired
	CalculateWinnerList calculateWinnerList;
	@Autowired
	CalculateStrengths calculateStrengths;
	@Autowired
	FormatResult formatResult;

	@Test
	void test() {
		List<List<CandidateResult>> winnerList = calculateWinnerList.apply(matrix,
				candidates);

		calculateStrengths.apply(candidates, winnerList);

		List<CandidatePair<String, Long>> cloudWords = makeCloudWordList
				.apply(candidates, winnerList);

		List<String> cws = new ArrayList<>();
		cloudWords.forEach(x -> cws.add(x.cdr() + "," + x.car() + ",#ffffff"));
		assertEquals(CLOUDWORDS_CSV, String.join("\n", cws));
		String report = formatResult.apply(candidates, winnerList);
		assertEquals(REPORT, report);
	}

}
