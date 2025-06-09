package civitas.common.election;

import civitas.common.ballotdesign.GetNthContext;
import civitas.util.Use;

public class GetBlockName {

	@Use
	GetNthContext getNthContext;
	@Use
	GetVoterBlockForBlock getVoterBlockForBlock;
	@Use
	GetContextForBlock getContextForBlock;

	public String apply(ElectionDetails that, int block) {
		int voterBlock = getVoterBlockForBlock.apply(that, block);
		int context = getContextForBlock.apply(that, block);
		return "voterBlock-" + voterBlock + "-context-"
				+ getNthContext.apply(that.ballotDesign, context);
	}

}
