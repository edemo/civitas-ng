package civitas.common.votersubmission;

import org.springframework.stereotype.Service;

@Service
public class GetMetaForVoterSubmission {

	@Deprecated
	public final String apply(int voterBlock) {
		return "voterSubmission-voterBlock" + voterBlock;
	}

}
