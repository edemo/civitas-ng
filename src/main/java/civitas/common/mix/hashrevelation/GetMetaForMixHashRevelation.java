package civitas.common.mix.hashrevelation;

import civitas.common.CommonConstants;
import civitas.common.election.ElectionDetails;
import civitas.common.election.GetBlockName;
import civitas.util.Use;

public class GetMetaForMixHashRevelation implements CommonConstants {

	@Use
	GetBlockName getBlockName;

	public String apply(final ElectionDetails electionDetails, boolean isVoteMix,
			int block, int tellerIndex) {
		String blockDesc = getBlockName.apply(electionDetails, block);
		return mixHashRevelationMETA_PREFIX
				+ (isVoteMix ? mixHashRevelationMETA_VOTE_REVELATION
						: mixHashRevelationMETA_ER_REVELATION)
				+ ":" + blockDesc + ":" + tellerIndex;
	}

}
