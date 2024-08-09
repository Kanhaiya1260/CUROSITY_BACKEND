package com.app.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.app.entities.Employee;
import com.app.entities.Project;
import com.app.entities.ProjectEmployeeDetails;
import com.app.entities.ProjectEmployeeId;

public interface ProjectEmployeeDetailsDao extends JpaRepository<ProjectEmployeeDetails, ProjectEmployeeId> {
//delete all details by specified emp id (use case : delete emp details)
	int deleteByMyEmployeeId(Long empId);

	// delete all details by specified project id (use case : delete project details)
	int deleteByMyProjectId(Long projectId);
	//get all emps by a project id  myProject
	@Query("select p.myEmployee from ProjectEmployeeDetails p where p.myProject.id=:projectId")
	List<Employee> findByProjectId(Long projectId);
	//get all projects assigned for the emp
	@Query("select p.myProject from ProjectEmployeeDetails p where p.myEmployee.id=:empId")
	List<Project> findByEmpId(Long empId);
	
	

}
