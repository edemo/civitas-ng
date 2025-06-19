package civitas.common.election;

import org.springframework.stereotype.Service;

@Service
public class GetElectionIdAsString {

	public String apply(ElectionID electionID) {
		return electionID.host + ":" + electionID.port + ":" + electionID.id;
	}

}
