package civitas.bboard.server;

import org.springframework.data.repository.CrudRepository;

public interface BoardRepository extends CrudRepository<Board, String> {

}
