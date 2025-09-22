package civitas.common.electionresults;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import civitas.common.CommonConstants;

@Controller
public class GetMetaForTeller implements CommonConstants {
	@Autowired
	GetComputerForTeller getComputerForTeller;

	public String appply(int tellerIndex) {
		return ElectionResultsMETA + getComputerForTeller.apply(tellerIndex);
	}

}
