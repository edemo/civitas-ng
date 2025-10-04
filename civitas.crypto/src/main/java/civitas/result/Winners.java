package civitas.result;

import java.util.List;

import lombok.NonNull;
import lombok.Value;

@Value
public class Winners {
	@NonNull List<List<Integer>> result;

	@NonNull CandidatePair<Integer, Integer>[][] beatpaths;
}
