package civitas.common.mix.capabilitymix;

import civitas.common.CommonConstants;

public class GetMixConfirmationMeta implements CommonConstants {

	public String apply(final boolean isVoteMix, final int speakerIndex, final int tellerIndex) {
		return MIX_CONFIRM_META_PREFIX + (isVoteMix ? MIX_CONFIRM_META_VOTE_REVELATION : MIX_CONFIRM_META_ER_REVELATION)
				+ ":" + speakerIndex + ":" + tellerIndex;
	}
}
