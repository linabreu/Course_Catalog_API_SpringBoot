package courseCatalog.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import courseCatalog.entity.Section;

public interface SectionDao extends JpaRepository<Section, Long> {}
