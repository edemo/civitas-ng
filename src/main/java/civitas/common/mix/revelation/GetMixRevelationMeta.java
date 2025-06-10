package civitas.common.mix.revelation;

import civitas.common.CommonConstants;
import civitas.common.election.ElectionDetails;
import civitas.common.election.GetBlockName;
import civitas.util.Use;

public class GetMixRevelationMeta implements CommonConstants {

	@Use
	GetBlockName getBlockName;

	public String apply(final ElectionDetails electionDetails, boolean isVoteMix,
			int block, int tellerIndex) {

		String blockDesc = electionDetails == null ? ""
				: getBlockName.apply(electionDetails, block);
		return mixRevelationMETA_PREFIX
				+ (isVoteMix ? mixRevelationMETA_VOTE_REVELATION
						: mixRevelationMETA_ER_REVELATION)
				+ ":" + blockDesc + ":" + tellerIndex;
	}

}
