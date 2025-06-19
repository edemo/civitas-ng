package civitas.common.mix.votemix;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import civitas.common.CommonConstants;
import civitas.common.election.ElectionDetails;
import civitas.common.election.GetBlockName;
import lombok.NonNull;

@Service
public class GetVoteMixMeta implements CommonConstants {

	@Autowired
	GetBlockName getBlockName;

	public String apply(@NonNull final ElectionDetails details, int block,
			int mixNumber, boolean rightMix) {
		String blockDesc = getBlockName.apply(details, block);
		return VoteMixMETA + blockDesc + ":" + mixNumber + (rightMix ? "R" : "L");
	}

}
