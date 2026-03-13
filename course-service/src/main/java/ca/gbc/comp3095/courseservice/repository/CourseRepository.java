package ca.gbc.comp3095.courseservice.repository;

/* JAMES - 
* No SQL yet
* Uses Spring Data JPA abstraction
* Microservice owns its data*
*/


import ca.gbc.comp3095.courseservice.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Long>{
}
