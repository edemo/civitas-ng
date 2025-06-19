package civitas.common.electionresults;

import org.springframework.stereotype.Service;

@Service
public class GetComputerForTeller {

	public String apply(int tellerIndex) {
		return "Teller" + tellerIndex;
	}

}
