package civitas.common.capabilitymix;

import civitas.common.CommonConstants;
import civitas.common.election.ElectionDetails;
import civitas.common.election.GetBlockName;
import civitas.util.Use;

public class GetCapabilityMixMeta implements CommonConstants {

	@Use
	GetBlockName getBlockName;

	public String apply(final ElectionDetails details, int block, int mixNumber,
			boolean rightMix) {
		if (null == details)
			throw new IllegalArgumentException("null block");
		String blockDesc = getBlockName.apply(details, block);
		return CapabilityMixMETA + blockDesc + ":" + mixNumber
				+ (rightMix ? "R" : "L");
	}

}
