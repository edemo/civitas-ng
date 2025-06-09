package civitas.common.election;

import civitas.common.ElectionID;

public class GetElectionIdAsString {

	public String apply(ElectionID electionID) {
		return electionID.host + ":" + electionID.port + ":" + electionID.id;
	}

}
