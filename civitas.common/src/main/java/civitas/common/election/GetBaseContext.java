package civitas.common.election;

import org.springframework.stereotype.Controller;

@Controller
public class GetBaseContext {

	public String apply(final ElectionDetails that, final int voterBlock) {
		return that.electionID + ':' + voterBlock + ':';
	}
}
