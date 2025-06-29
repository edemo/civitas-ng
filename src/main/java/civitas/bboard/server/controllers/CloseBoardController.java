package civitas.bboard.server.controllers;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import civitas.bboard.server.Board;
import civitas.bboard.server.BoardRepository;
import civitas.bboard.server.GetBoardForId;
import civitas.common.board.BoardClosedContentCommitment;
import civitas.common.election.ElectionID;
import civitas.crypto.CryptoBase;
import civitas.crypto.messagedigest.CryptoHash;
import civitas.crypto.rsapublickey.ConvertStringToPublicKey;
import civitas.crypto.rsapublickey.VerifyPublicKeySignature;
import civitas.crypto.signature.Signature;
import lombok.NonNull;

@Controller
public class CloseBoardController {

	@Autowired
	VerifyPublicKeySignature verifyPublicKeySignature;
	@Autowired
	BoardRepository boardRepository;
	@Autowired
	CryptoBase cryptoBase;
	@Autowired
	CryptoHash cryptoHash;
	@Autowired
	GetBoardForId getBoardForId;
	@Autowired
	GetRestTemplate getRestTemplate;
	@Autowired
	ConvertStringToPublicKey convertStringToPublicKey;

	@PostMapping("/boards/close/{bbid}")
	public boolean apply(@PathVariable("bbid") String bbid,
			@NonNull ElectionID postHashTo, int numVoterBlocks,
			@NonNull Signature sig)
			throws IOException, InvalidKeySpecException, NoSuchAlgorithmException {

		if (numVoterBlocks < 0)
			throw new IllegalArgumentException("number of voter blocks is negative");
		Board board = getBoardForId.apply(bbid, true);
		PublicKey key = convertStringToPublicKey.apply(board.keyString);
		boolean res = verifyPublicKeySignature.apply(sig, key, bbid);
		if (res) {

			List<String> hashes = new ArrayList<>();
			for (int i = 0; i < numVoterBlocks; i++) {
				String meta = "voterSubmission-voterBlock" + i;
				hashes.add(Base64.getEncoder().encodeToString(
						cryptoHash.apply(bbid.getBytes(), meta.getBytes())));
			}
			BoardClosedContentCommitment bccc = new BoardClosedContentCommitment(
					postHashTo, bbid, hashes);
			String uriBase = postHashTo.uriBase;
			String uri = uriBase + "/post";

			getRestTemplate.apply().postForObject(uri, bccc, Boolean.class);
			board.isOpen = false;
			boardRepository.save(board);

		}
		return res;
	}

}
