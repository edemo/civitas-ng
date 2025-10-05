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

	public void apply(final String bbid, final String meta, final String mesg, final long t)
			throws JAXBException, IOException {

		Optional<ElectionCache> cachep = electionCacheRepository.findById(bbid);
		if (!cachep.isPresent()) {
			throw new IllegalArgumentException("no cache");
		}

		ElectionCache cache = cachep.get();

		if (CommonConstants.ELECTION_EVENT_META.equals(meta)) {
			ElectionEvent e = convertFromXml.apply(mesg, ElectionEvent.class);
			if (ElectionEvent.EVENT_KIND_FINALIZE.equals(e.kind())) {
				// FIXME: tally
				cache.electionFinalizeTime = t;
			} else if (ElectionEvent.EVENT_KIND_START.equals(e.kind())) {
				cache.electionStartTime = t;
			} else if (ElectionEvent.EVENT_KIND_STOP.equals(e.kind())) {
				cache.electionStopTime = t;
			}
		}

		electionCacheRepository.save(cache);
	}
}
