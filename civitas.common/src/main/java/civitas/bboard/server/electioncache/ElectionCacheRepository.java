package civitas.bboard.server.electioncache;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

@Component
public interface ElectionCacheRepository
		extends CrudRepository<ElectionCache, String> {

}
