package com.app.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.app.entities.Department;

public interface DepartmentDao extends JpaRepository<Department, Long> {
	// get department n emp details in a single join query
	@Query("select d from Department d left join fetch d.emps where d.id=?1")
	Department getDepartmentAndEmpDetails(Long deptId);
}
