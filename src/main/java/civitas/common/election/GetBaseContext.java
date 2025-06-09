package civitas.common.election;

import civitas.util.Use;

public class GetBaseContext {
	@Use
	GetElectionIdAsString getElectionIdAsString;

	public String apply(ElectionDetails that, int voterBlock) {
		return getElectionIdAsString.apply(that.electionID) + ':' + voterBlock
				+ ':';
	}

}
