package courseCatalog.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import courseCatalog.entity.Instructor;

public interface InstructorDao extends JpaRepository<Instructor, Long> {}
