package courseCatalog.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import courseCatalog.entity.Course;

public interface CourseDao extends JpaRepository<Course, Long> {}
