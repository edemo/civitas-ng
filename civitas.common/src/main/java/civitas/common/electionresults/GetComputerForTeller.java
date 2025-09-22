package civitas.common.electionresults;

import org.springframework.stereotype.Controller;

@Controller
public class GetComputerForTeller {

	public String apply(int tellerIndex) {
		return "Teller" + tellerIndex;
	}
}
