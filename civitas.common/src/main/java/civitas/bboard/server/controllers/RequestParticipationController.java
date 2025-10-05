package civitas.bboard.server.controllers;

import java.io.IOException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import org.bouncycastle.crypto.CryptoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import civitas.bboard.server.electioncache.ElectionCache;
import civitas.bboard.server.electioncache.ElectionCacheRepository;
import civitas.common.CommonConstants;
import civitas.common.Configuration;
import civitas.common.ServerRole;
import civitas.common.election.ElectionDetails;
import civitas.common.election.ElectionStatus;
import civitas.crypto.rsapublickey.ConvertStringToPublicKey;
import jakarta.xml.bind.JAXBException;

@Controller
public class RequestParticipationController implements CommonConstants {

	@Autowired
	ElectionCacheRepository electionCacheRepository;

	@Autowired
	PostController postController;

	@Autowired
	NewBoardController newBoardController;

	@Autowired
	ConvertStringToPublicKey convertStringToPublicKey;

	@Autowired
	Configuration configuration;

	@PostMapping("/requestParticipation")
	@ResponseBody
	public String apply(@RequestBody final RequestParticipationDTO participationRequest)
			throws UnrecoverableKeyException, KeyStoreException, NoSuchAlgorithmException, CertificateException,
					JAXBException, IOException, CommunicableException, CryptoException {
		if (participationRequest == null) {
			return null;
		}
		ElectionDetails electionDetails = new ElectionDetails(
				participationRequest.electionID(),
				participationRequest.supervisorPubkey(),
				participationRequest.registrarPubKey(),
				participationRequest.name(),
				participationRequest.description(),
				participationRequest.version(),
				participationRequest.ballotDesign(),
				participationRequest.startTime(),
				participationRequest.stopTime(),
				participationRequest.finalizeTime(),
				participationRequest.elGamalP(),
				participationRequest.elGamalQ(),
				participationRequest.elGamalG(),
				participationRequest.sharedKeyLength(),
				participationRequest.nonceLength(),
				participationRequest.voterAnonymityParam());
		PublicKey supervisorPubKey = convertStringToPublicKey.apply(participationRequest.supervisorPubkey());
		String boardId = newBoardController.apply(supervisorPubKey);
		int myIndex = participationRequest.tellerDetails().stream()
				.map(host -> ServerRole.BBS.equals(host.getRole())
						&& host.getUrlbase().equals(configuration.urlBase))
				.toList()
				.indexOf(true);
		ElectionCache electionCache = new ElectionCache(boardId, myIndex, ElectionStatus.DEFINED, electionDetails);
		electionCacheRepository.save(electionCache);

		postController.apply(boardId, ELECTION_DETAILS_META, electionDetails);
		return boardId;
	}
}
