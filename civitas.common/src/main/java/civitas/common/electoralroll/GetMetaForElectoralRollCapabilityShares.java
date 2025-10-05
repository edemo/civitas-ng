package civitas.common.electoralroll;

import civitas.common.CommonConstants;

public class GetMetaForElectoralRollCapabilityShares implements CommonConstants {
	public String apply(final int tellerIndex, final int voterBlock) {
		return ELECTORAL_ROLL_CAPABILITY_SHARES_META + ":teller:" + tellerIndex + ":voterBlock:" + voterBlock;
	}
}
