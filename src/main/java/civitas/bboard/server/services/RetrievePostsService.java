package civitas.bboard.server.services;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import civitas.bboard.common.BBPost;
import civitas.bboard.common.BBPostRepository;
import civitas.bboard.server.GetBoardForId;

@Service
public class RetrievePostsService {
	@Autowired
	GetBoardForId getBoardForId;
	@Autowired
	BBPostRepository bBPostRepository;

	@GetMapping("/boards/{bbid}")
	private Iterable<BBPost> apply(@PathVariable("bbid") String bbid)
			throws IOException {
		getBoardForId.apply(bbid, true);

		Iterable<BBPost> posts = bBPostRepository.findByBbid(bbid);
		return posts;

	}

}
