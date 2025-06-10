package civitas.common.election;

public class GetElectionIdAsString {

	public String apply(ElectionID electionID) {
		return electionID.host + ":" + electionID.port + ":" + electionID.id;
	}

}
