package civitas.common.mix.votemix;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import civitas.common.CommonConstants;
import civitas.common.election.ElectionDetails;
import civitas.common.election.GetBlockName;
import jakarta.annotation.Nonnull;

@Controller
public class GetVoteMixMeta implements CommonConstants {

	@Autowired
	GetBlockName getBlockName;

	public String apply(
			@Nonnull final ElectionDetails details, final int block, final int mixNumber, final boolean rightMix) {
		if (null == details) {
			throw new NullPointerException();
		}
		String blockDesc = getBlockName.apply(details, block);
		return VOTE_MIX_META + blockDesc + ":" + mixNumber + (rightMix ? "R" : "L");
	}
}
