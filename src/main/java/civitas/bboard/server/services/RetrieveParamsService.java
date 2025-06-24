package civitas.bboard.server.services;

import java.io.IOException;

import org.slf4j.MarkerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import civitas.bboard.common.BBPost;
import civitas.bboard.common.BBPostRepository;
import civitas.bboard.server.GetBoardForId;
import civitas.common.LoggerService;

@Service
public class RetrieveParamsService {

	@Autowired
	GetBoardForId getBoardForId;
	@Autowired
	LoggerService loggerService;
	@Autowired
	BBPostRepository bBPostRepository;

	@GetMapping("/boards/{bbid}/{fromTime}-{toTime}-{metaCriteria}")
	private Iterable<BBPost> retrieve_params(@PathVariable("bbid") String bbid,
			@PathVariable("fromTime") Long fromTime,
			@PathVariable("toTime") Long toTime,
			@PathVariable("metaCriteria") String metaCriteria) throws IOException {

		getBoardForId.apply(bbid, true);

		loggerService.apply(MarkerFactory.getMarker("bbs_retrieve"), metaCriteria);
		return bBPostRepository.findByBbidAndTimestampBetweenAndMeta(bbid,
				fromTime, toTime, metaCriteria);
	}

}
