package civitas.result;

import java.util.List;

import lombok.Data;
import lombok.NonNull;

@Data
public class Winners {
	@NonNull
	List<List<Integer>> result;
	@NonNull
	Pair<Integer, Integer>[][] beatpaths;

}
