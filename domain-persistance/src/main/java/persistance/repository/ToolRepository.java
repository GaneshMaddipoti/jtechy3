package persistance.repository;

import domain.model.Tool;
import org.springframework.data.repository.CrudRepository;

public interface ToolRepository extends CrudRepository<Tool, Integer> {

}
