package civitas.bboard.server.services;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

import civitas.bboard.server.electioncache.ElectionCache;
import civitas.bboard.server.electioncache.ElectionCacheRepository;
import civitas.common.Host;
import civitas.common.election.ElectionDetails;
import civitas.common.election.ElectionStatus;
import civitas.crypto.CryptoError;

@Service
public class RequestParticipationService {

	@Autowired
	ElectionCacheRepository electionCacheRepository;
	@Autowired
	PostService postService;

	@PostMapping("/requestParticipation")
	private boolean requestParticipation(ElectionDetails elecDetails,
			Host[] tellerDetails) throws IOException, InvalidKeyException,
			NoSuchAlgorithmException, NoSuchProviderException, SignatureException,
			CryptoError, IllegalArgumentException, InvalidKeySpecException {

		if (elecDetails == null || elecDetails.electionID == null)
			return false;

		Optional<ElectionCache> electionCachep = null;
		// electionCacheRepository.findById(elecDetails.electionID);
		ElectionCache electionCache;
		if (electionCachep.isEmpty()) {
			// FIXME: try to get the election Cache from the master
			return false;
		} else {
			electionCache = electionCachep.get();
		}
		if (electionCache.getStatus() != ElectionStatus.DEFINED)
			return false;
		// electionCacheRepository.save(electionCache);

//		postService.apply(elecDetails.electionID, "supervisor",
//				elecDetails.supervisor);// FIXME is it okay?

		return true;
	}

}
