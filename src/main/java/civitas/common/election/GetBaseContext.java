package civitas.common.election;

import org.springframework.stereotype.Service;

@Service
public class GetBaseContext {

	public String apply(ElectionDetails that, int voterBlock) {
		return that.electionID + ':' + voterBlock + ':';
	}

}
