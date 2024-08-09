package com.app.dao;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.entities.Employee;

public interface EmployeeDao extends JpaRepository<Employee, Long> {
	List<Employee> findByDeptId(long deptId);
	//finder method for authentication
		Optional<Employee> findByEmailAndPassword(String em, String pass);

		// add a finder : JPQL : select e from Employee e where e.dept.id=:id
		List<Employee> findByDeptId(Long departmentId);

//		List all emp details , joined between a date range
		List<Employee> findByJoinDateBetween(LocalDate start, LocalDate end);

//		Display all emp details , containing 
		// specified keyword in their last name
		List<Employee> findByLastNameContaining(String keyword);

		// Display all emp details, drawing salary>=specific salary
		List<Employee> findBySalaryGreaterThanEqual(double minSal);

//		Display top 2 salaried emps
		List<Employee> findTop2ByOrderBySalaryDesc();

		// list emps whose adhaar card is created before specific date
		List<Employee> findByCardCreatedOnBefore(LocalDate date);
		//list emps  by in time
		List<Employee> findByInTime(LocalDateTime inTime);
		
}
