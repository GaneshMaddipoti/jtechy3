package persistance.repository;

import domain.model.EntityInfo;
import org.springframework.data.repository.CrudRepository;

public interface EntityInfoRepository extends CrudRepository<EntityInfo, Integer> {

}
