package civitas.common.election;

import org.springframework.stereotype.Controller;

@Controller
public class GetBaseContext {

	public String apply(ElectionDetails that, int voterBlock) {
		return that.electionID + ':' + voterBlock + ':';
	}
}
