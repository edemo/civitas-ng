package civitas.result;

import java.util.List;

import lombok.Data;
import lombok.NonNull;

@Data
public class CandidateResult {
	@NonNull
	Integer candidate;
	@NonNull
	List<Integer> beatenCandidates;
	@NonNull
	List<Integer> beat;
	@NonNull
	List<Integer> beaten;
	@NonNull
	Double strength;

}
