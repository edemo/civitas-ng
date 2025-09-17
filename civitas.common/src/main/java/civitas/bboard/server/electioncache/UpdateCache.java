package civitas.bboard.server.electioncache;

import java.io.IOException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import civitas.common.CommonConstants;
import civitas.common.ConvertFromXml;
import civitas.common.election.ElectionEvent;
import jakarta.xml.bind.JAXBException;

@Controller
public class UpdateCache {

	@Autowired
	ConvertFromXml convertFromXml;
	@Autowired
	ElectionCacheRepository electionCacheRepository;

	public void apply(String bbid, String meta, String mesg, long t)
			throws JAXBException, IOException {

		Optional<ElectionCache> cachep = electionCacheRepository.findById(bbid);
		if (!cachep.isPresent()) {
			throw new IllegalArgumentException("no cache");
		}

		ElectionCache cache = cachep.get();

		if (CommonConstants.ElectionEventMETA.equals(meta)) {
			ElectionEvent e = convertFromXml.apply(mesg, ElectionEvent.class);
			if (ElectionEvent.EVENT_KIND_FINALIZE.equals(e.getKind())) {
				// FIXME: tally
				cache.electionFinalizeTime = t;
			} else if (ElectionEvent.EVENT_KIND_START.equals(e.getKind())) {
				cache.electionStartTime = t;
			} else if (ElectionEvent.EVENT_KIND_STOP.equals(e.getKind())) {
				cache.electionStopTime = t;
			}
		}

		electionCacheRepository.save(cache);
	}

}
