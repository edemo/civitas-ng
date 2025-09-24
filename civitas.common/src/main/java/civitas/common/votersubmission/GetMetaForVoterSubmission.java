package civitas.common.votersubmission;

import org.springframework.stereotype.Controller;

@Controller
public class GetMetaForVoterSubmission {

	@Deprecated
	public final String apply(final int voterBlock) {
		return "voterSubmission-voterBlock" + voterBlock;
	}
}
