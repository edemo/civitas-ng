package civitas.common.tabteller.voterblockcontents;

public class GetTabTellerVoterBlockContentsMeta {

	public String apply(int tellerIndex, int voterBlock) {
		return "ttVoterBlockContents:teller" + tellerIndex + ":voterBlock"
				+ voterBlock;
	}

}
