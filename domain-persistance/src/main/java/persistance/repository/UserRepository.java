package persistance.repository;

import domain.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserRepository extends CrudRepository<User, Integer> {
	
	List<User> findByUserName(String userName);

}
