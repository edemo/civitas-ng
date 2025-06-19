package civitas.common.election;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GetBaseContext {
	@Autowired
	GetElectionIdAsString getElectionIdAsString;

	public String apply(ElectionDetails that, int voterBlock) {
		return getElectionIdAsString.apply(that.electionID) + ':' + voterBlock
				+ ':';
	}

}
