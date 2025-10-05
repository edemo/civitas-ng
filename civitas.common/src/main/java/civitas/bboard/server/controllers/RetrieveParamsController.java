package civitas.bboard.server.controllers;

import org.slf4j.MarkerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import civitas.bboard.common.BBPost;
import civitas.bboard.common.BBPostRepository;
import civitas.bboard.server.GetBoardForId;
import civitas.common.LoggerService;

@Controller
public class RetrieveParamsController {

	@Autowired
	GetBoardForId getBoardForId;

	@Autowired
	LoggerService loggerController;

	@Autowired
	BBPostRepository bBPostRepository;

	@GetMapping("/boards/{bbid}/{fromTime}-{toTime}-{metaCriteria}")
	Iterable<BBPost> apply(
			@PathVariable("bbid") final String bbid,
			@PathVariable("fromTime") final Long fromTime,
			@PathVariable("toTime") final Long toTime,
			@PathVariable("metaCriteria") final String metaCriteria) {

		getBoardForId.apply(bbid, true);

		loggerController.apply(MarkerFactory.getMarker("bbs_retrieve"), metaCriteria);
		return bBPostRepository.findByBbidAndTimestampBetweenAndMeta(bbid, fromTime, toTime, metaCriteria);
	}
}
