package courseCatalog.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import courseCatalog.entity.Department;

public interface DepartmentDao extends JpaRepository<Department, Long> {}
