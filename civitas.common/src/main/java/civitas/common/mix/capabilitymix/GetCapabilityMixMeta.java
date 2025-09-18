package civitas.common.mix.capabilitymix;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import civitas.common.CommonConstants;
import civitas.common.election.ElectionDetails;
import civitas.common.election.GetBlockName;

@Controller
public class GetCapabilityMixMeta implements CommonConstants {

	@Autowired
	GetBlockName getBlockName;

	public String apply(final ElectionDetails details, int block, int mixNumber, boolean rightMix) {
		if (null == details) {
			throw new IllegalArgumentException("null block");
		}
		String blockDesc = getBlockName.apply(details, block);
		return CapabilityMixMETA + blockDesc + ":" + mixNumber + (rightMix ? "R" : "L");
	}
}
