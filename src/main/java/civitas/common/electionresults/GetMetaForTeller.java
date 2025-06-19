package civitas.common.electionresults;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import civitas.common.CommonConstants;

@Service
public class GetMetaForTeller implements CommonConstants {
	@Autowired
	GetComputerForTeller getComputerForTeller;

	public String appply(int tellerIndex) {
		return ElectionResultsMETA + getComputerForTeller.apply(tellerIndex);
	}

}
