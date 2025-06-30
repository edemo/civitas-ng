package civitas.bboard.server.controllers;

import java.io.IOException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import civitas.bboard.server.GetBoardForId;
import civitas.bboard.server.electioncache.ElectionCache;
import civitas.bboard.server.electioncache.ElectionCacheRepository;
import civitas.common.election.ElectionDetails;
import civitas.common.election.ElectionStatus;

@Controller
public class ConfirmParticipationController {

	@Autowired
	GetBoardForId getBoardForId;
	@Autowired
	ElectionCacheRepository electionCacheRepository;

	@PostMapping("/confirmParticipation")
	private boolean confirmParticipation(ElectionDetails elecDetails, int index)
			throws IOException {
		getBoardForId.apply(elecDetails.getElectionID(), true);
		Optional<ElectionCache> electionp = electionCacheRepository
				.findById(elecDetails.getElectionID());
		if (electionp.isPresent())
			throw new IllegalArgumentException("we are already participating");
		ElectionCache election = new ElectionCache(elecDetails.getElectionID(),
				index, ElectionStatus.CREATED, elecDetails);
		electionCacheRepository.save(election);
		return true;
	}

}
