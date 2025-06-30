package civitas.common.mix.hashrevelation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import civitas.common.CommonConstants;
import civitas.common.election.ElectionDetails;
import civitas.common.election.GetBlockName;

@Controller
public class GetMetaForMixHashRevelation implements CommonConstants {

	@Autowired
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
