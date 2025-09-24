package civitas.common.tabteller.voterblockcontents;

public class GetTabTellerVoterBlockContentsMeta {

	public String apply(final int tellerIndex, final int voterBlock) {
		return "ttVoterBlockContents:teller" + tellerIndex + ":voterBlock" + voterBlock;
	}
}
