package civitas.common.election;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import civitas.common.ballotdesign.GetNthContext;

@Controller
public class GetBlockName {

	@Autowired
	GetNthContext getNthContext;

	@Autowired
	GetVoterBlockForBlock getVoterBlockForBlock;

	@Autowired
	GetContextForBlock getContextForBlock;

	public String apply(ElectionDetails that, int block) {
		int voterBlock = getVoterBlockForBlock.apply(that, block);
		int context = getContextForBlock.apply(that, block);
		return "voterBlock-" + voterBlock + "-context-" + getNthContext.apply(that.ballotDesign, context);
	}
}
