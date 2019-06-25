package persistance.repository;

import domain.model.Concept;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ConceptRepository extends CrudRepository<Concept, Integer> {
	
	List<Concept> findByName(String name);

}
