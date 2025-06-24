package civitas.bboard.server.electioncache;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import civitas.common.CommonConstants;
import civitas.common.ConvertFromXml;
import civitas.common.election.ElectionEvent;

@Service
public class UpdateCache {

	@Autowired
	ConvertFromXml convertFromXml;
	@Autowired
	ElectionCacheRepository electionCacheRepository;

	public void apply(String bbid, String meta, String mesg, long t)
			throws JsonMappingException, JsonProcessingException {

		Optional<ElectionCache> cachep = null;// electionCacheRepository.findById(bbid);
		ElectionCache cache;
		if (cachep.isEmpty())
			throw new IllegalArgumentException("no cache");
		cache = cachep.get();

		if (meta.equals(CommonConstants.ElectionEventMETA)) {
			ElectionEvent e = convertFromXml.apply(mesg, ElectionEvent.class);
			if (ElectionEvent.EVENT_KIND_FINALIZE.equals(e)) {
				// FIXME: tally
				cache.electionFinalizeTime = t;
			} else if (ElectionEvent.EVENT_KIND_START.equals(e)) {
				cache.electionStartTime = t;
			} else if (ElectionEvent.EVENT_KIND_STOP.equals(e)) {
				cache.electionStopTime = t;
			}
		}

		// electionCacheRepository.save(cache);
	}

}
