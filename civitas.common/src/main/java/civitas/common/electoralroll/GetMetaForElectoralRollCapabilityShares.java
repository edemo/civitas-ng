package civitas.common.electoralroll;

import civitas.common.CommonConstants;

public class GetMetaForElectoralRollCapabilityShares implements CommonConstants {
	public String apply(final int tellerIndex, final int voterBlock) {
		return ElectoralRollCapabilitySharesMETA + ":teller:" + tellerIndex + ":voterBlock:" + voterBlock;
	}
}
