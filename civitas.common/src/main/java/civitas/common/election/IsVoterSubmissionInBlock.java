package civitas.common.election;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import civitas.common.ballotdesign.GetNthContext;
import civitas.common.capabilityencryption.VoterEncCapabilities;
import civitas.common.votersubmission.VoterSubmission;

@Controller
public class IsVoterSubmissionInBlock {
	@Autowired
	GetVoterBlockForBlock getVoterBlockForBlock;

	@Autowired
	GetNthContext getNthContext;

	@Autowired
	GetContextForBlock getContextForBlock;

	@Autowired
	GetBaseContext getBaseContext;

	public boolean apply(final ElectionDetails that, final VoterSubmission voter, final int block) {
		if (voter == null) {
			throw new IllegalArgumentException("null voter");
		}
		int voterBlock = getVoterBlockForBlock.apply(that, block);
		return voterBlock == voter.voterBlock;
	}

	public boolean apply(
			final ElectionDetails that, final VoterEncCapabilities voter, final String context, final int block) {
		if (voter == null) {
			throw new IllegalArgumentException("null voter");
		}
		int voterBlock = getVoterBlockForBlock.apply(that, block);
		int contextN = getContextForBlock.apply(that, block);
		String targetContext = getNthContext.apply(that.ballotDesign, contextN);
		return voterBlock == voter.voterBlock && targetContext.equals(context);
	}

	public boolean apply(
			final ElectionDetails that, final VoterSubmission voter, final String context, final int block) {
		if (voter == null) {
			throw new IllegalArgumentException("null voter");
		}

		int voterBlock = getVoterBlockForBlock.apply(that, block);
		int contextN = getContextForBlock.apply(that, block);
		String targetContext =
				getBaseContext.apply(that, voterBlock) + getNthContext.apply(that.ballotDesign, contextN);
		return voterBlock == voter.voterBlock && targetContext.equals(context);
	}
}
