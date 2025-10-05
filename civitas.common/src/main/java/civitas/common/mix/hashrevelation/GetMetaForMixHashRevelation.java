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

	public String apply(
			final ElectionDetails electionDetails, final boolean isVoteMix, final int block, final int tellerIndex) {
		String blockDesc = getBlockName.apply(electionDetails, block);
		return MIX_HASH_REVELATION_META_PREFIX
				+ (isVoteMix ? MIX_HASH_REVELATION_META_VOTE_REVELATION : MIX_HASH_REVELATION_META_ER_REVELATION)
				+ ":" + blockDesc + ":" + tellerIndex;
	}
}
