package civitas.bboard.server.controllers;

import java.math.BigInteger;
import java.security.PublicKey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import civitas.bboard.server.Board;
import civitas.bboard.server.BoardRepository;
import civitas.common.CommonConstants;
import civitas.crypto.algorithms.CreateFreshNonce;
import civitas.crypto.rsapublickey.ConvertPublicKeyToString;

@Controller
public class NewBoardController implements CommonConstants {

	@Autowired
	CreateFreshNonce createFreshNonce;

	@Autowired
	BoardRepository boardRepository;

	@Autowired
	ConvertPublicKeyToString convertPublicKeyToString;

	@PostMapping("/boards/newBoard")
	public String apply(final PublicKey key) {
		String newBoardName;
		do {
			byte[] bs = createFreshNonce.apply(ELECTION_ID_LENGTH);
			newBoardName = new BigInteger(bs).abs().toString(16);
		} while (boardRepository.findById(newBoardName).isPresent());

		String keyString = convertPublicKeyToString.apply(key);
		boardRepository.save(new Board(newBoardName, keyString, true));
		return newBoardName;
	}
}
