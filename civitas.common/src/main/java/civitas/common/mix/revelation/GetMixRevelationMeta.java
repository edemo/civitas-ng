package civitas.common.mix.revelation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import civitas.common.CommonConstants;
import civitas.common.election.ElectionDetails;
import civitas.common.election.GetBlockName;
import jakarta.annotation.Nonnull;

@Controller
public class GetMixRevelationMeta implements CommonConstants {

	@Autowired
	GetBlockName getBlockName;

	public String apply(@Nonnull final ElectionDetails electionDetails, boolean isVoteMix, int block, int tellerIndex) {
		if (null == electionDetails) {
			throw new NullPointerException();
		}

		String blockDesc = getBlockName.apply(electionDetails, block);
		return mixRevelationMETA_PREFIX
				+ (isVoteMix ? mixRevelationMETA_VOTE_REVELATION : mixRevelationMETA_ER_REVELATION)
				+ ":" + blockDesc + ":" + tellerIndex;
	}
}
