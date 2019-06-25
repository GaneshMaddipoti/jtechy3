package persistance.repository;

import domain.model.Course;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CourseRepository extends CrudRepository<Course, Integer> {

    @Query("SELECT c FROM Course c WHERE c.active = :active")
    public List<Course> findByActive(@Param("active") boolean active);

    @Query("SELECT DISTINCT category FROM Course WHERE active = 1")
    List<String> findDistinctCategories();

}
