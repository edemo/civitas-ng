package civitas.common.electionresults;

import civitas.common.CommonConstants;
import civitas.util.Use;

public class GetMetaForTeller implements CommonConstants {
	@Use
	GetComputerForTeller getComputerForTeller;

	public String appply(int tellerIndex) {
		return ElectionResultsMETA + getComputerForTeller.apply(tellerIndex);
	}

}
