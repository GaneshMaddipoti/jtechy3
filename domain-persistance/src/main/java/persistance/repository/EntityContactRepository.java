package persistance.repository;


import domain.model.EntityContact;
import org.springframework.data.repository.CrudRepository;

public interface EntityContactRepository extends CrudRepository<EntityContact, Integer> {

}
