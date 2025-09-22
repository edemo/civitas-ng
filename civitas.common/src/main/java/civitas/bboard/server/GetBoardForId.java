package civitas.bboard.server;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class GetBoardForId {

	@Autowired
	BoardRepository boardRepository;

	public Board apply(String bbid, boolean mustBeOpen) {
		Optional<Board> boardp = boardRepository.findById(bbid);
		if (!boardp.isPresent()) {
			throw new IllegalArgumentException("no such board is open");
		}
		Board board = boardp.get();
		if (mustBeOpen && !board.isOpen) {
			throw new IllegalArgumentException("No such board is open");
		}
		return board;
	}
}
