package civitas.bboard.server.services;

import java.io.IOException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import civitas.bboard.server.Board;
import civitas.bboard.server.BoardRepository;
import civitas.bboard.server.GetBoardForId;
import civitas.common.board.BoardClosedContentCommitment;
import civitas.common.election.ElectionID;
import civitas.crypto.CryptoBase;
import civitas.crypto.messagedigest.CryptoHash;
import civitas.crypto.publickeymsg.PublicKeyMsg;
import civitas.crypto.rsapublickey.PublicKey;
import civitas.crypto.rsapublickey.VerifyPublicKeySignature;
import civitas.crypto.signature.Signature;
import lombok.NonNull;

@Service
public class CloseBoardService {

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
	RestClient restClient;

	@PostMapping("/boards/close/{bbid}")
	public boolean apply(@PathVariable("bbid") String bbid,
			@NonNull ElectionID postHashTo, int numVoterBlocks,
			@NonNull Signature sig) throws IOException, InvalidKeySpecException {

		if (numVoterBlocks < 0)
			throw new IllegalArgumentException("number of voter blocks is negative");
		Board board = getBoardForId.apply(bbid, true);
		PublicKey owner = new PublicKey(
				cryptoBase.publicKeyFactory.generatePublic(
						new X509EncodedKeySpec(Base64.getDecoder().decode(board.keyData))),
				board.keyName);

		boolean res = false;
		PublicKeyMsg msg = new PublicKeyMsg(bbid);
		res = verifyPublicKeySignature.apply(owner, sig, msg);
		if (res) {

			String[] hashes = new String[numVoterBlocks];
			for (int i = 0; i < numVoterBlocks; i++) {
				String meta = "voterSubmission-voterBlock" + i;
				hashes[i] = Base64.getEncoder()
						.encodeToString(cryptoHash.apply(bbid.getBytes(), meta.getBytes()));
			}
			BoardClosedContentCommitment bccc = new BoardClosedContentCommitment(
					postHashTo, bbid, hashes);
			String uriBase = postHashTo.uriBase;
			String uri = uriBase + "/post";

			restClient.restTemplate.postForObject(uri, bccc, Boolean.class);
			board.isOpen = false;
			boardRepository.save(board);

		}
		return res;
	}

}
