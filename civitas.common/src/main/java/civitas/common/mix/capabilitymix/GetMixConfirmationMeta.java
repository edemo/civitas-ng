package civitas.common.mix.capabilitymix;

import civitas.common.CommonConstants;

public class GetMixConfirmationMeta implements CommonConstants {

	public String apply(final boolean isVoteMix, final int speakerIndex, final int tellerIndex) {
		return mixConfirmMETA_PREFIX + (isVoteMix ? mixConfirmMETA_VOTE_REVELATION : mixConfirmMETA_ER_REVELATION) + ":"
				+ speakerIndex + ":" + tellerIndex;
	}
}
