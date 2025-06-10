package civitas.common.mix.votemix;

import civitas.common.CommonConstants;
import civitas.common.election.ElectionDetails;
import civitas.common.election.GetBlockName;
import civitas.util.Use;

public class GetVoteMixMeta implements CommonConstants {

	@Use
	GetBlockName getBlockName;

	public String meta(final ElectionDetails details, int block, int mixNumber,
			boolean rightMix) {
		String blockDesc = details == null ? ""
				: getBlockName.apply(details, block);
		return VoteMixMETA + blockDesc + ":" + mixNumber + (rightMix ? "R" : "L");
	}

}
