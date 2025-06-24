package civitas.bboard.server.services;

import java.io.IOException;
import java.math.BigInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

import civitas.bboard.server.BoardRepository;
import civitas.common.CommonConstants;
import civitas.crypto.algorithms.CreateFreshNonce;
import civitas.util.KeyOnWire;

@Service
public class NewBoardService implements CommonConstants {

	@Autowired
	CreateFreshNonce createFreshNonce;
	@Autowired
	BoardRepository boardRepository;

	@PostMapping("/boards/newBoard")
	private String newBoard(KeyOnWire key) throws IOException {
		String newBoardName;
		do {
			byte[] bs = createFreshNonce.apply(ELECTION_ID_LENGTH);
			newBoardName = new BigInteger(bs).abs().toString(16);
		} while (boardRepository.findById(newBoardName).isPresent());

		// boardRepository.save(new Board(newBoardName, key));
		return newBoardName;
	}

}
