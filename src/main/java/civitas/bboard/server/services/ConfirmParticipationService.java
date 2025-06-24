package civitas.bboard.server.services;

import java.io.IOException;
import java.util.HashMap;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

import civitas.bboard.server.GetBoardForId;
import civitas.bboard.server.electioncache.ElectionCache;
import civitas.bboard.server.electioncache.ElectionCacheRepository;
import civitas.common.election.ElectionDetails;
import civitas.common.election.ElectionStatus;

@Service
public class ConfirmParticipationService {

	@Autowired
	GetBoardForId getBoardForId;
	@Autowired
	ElectionCacheRepository electionCacheRepository;

	@PostMapping("/confirmParticipation")
	private boolean confirmParticipation(ElectionDetails elecDetails, int index)
			throws IOException {
		getBoardForId.apply(elecDetails.electionID, true);
		Optional<ElectionCache> electionp = electionCacheRepository
				.findById(elecDetails.electionID);
		if (electionp.isPresent())
			throw new IllegalArgumentException("we are already participating");
		ElectionCache election = new ElectionCache(elecDetails.electionID, index,
				ElectionStatus.CREATED, elecDetails, new HashMap<>());
		electionCacheRepository.save(election);
		return true;
	}

}
