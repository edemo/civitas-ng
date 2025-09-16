package civitas.bboard.common;

import org.springframework.data.repository.CrudRepository;

public interface BBPostRepository extends CrudRepository<BBPost, String> {

	Iterable<BBPost> findByBbidAndTimestampBetweenAndMeta(String bbid, Long tsmin,
			Long tsmax, String meta);

	Iterable<BBPost> findByBbid(String bbid);

	Iterable<BBPost> findByBbidOrderBySerialDesc(String bbid);

}
