package com.app.service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.app.dto.ApiResponse;
import com.app.dto.EmployeeDTO;


public interface EmployeeService {
//get list of emps from a specific dept
	List<EmployeeDTO> getAllEmployeesFromDept(Long deptId);

//delete emp details
	ApiResponse deleteEmpDetails(Long empId);

	EmployeeDTO addNewEmployee(EmployeeDTO dto);

	EmployeeDTO updateEmployee(Long empId, EmployeeDTO dto);

	EmployeeDTO getEmpDetails(Long deptId, Long empId);

	// get all emps : pagination
	List<EmployeeDTO> getAllEmployees(int pageNumber, int pageSize);

	// add emp n it's image details in a single request
	EmployeeDTO addNewEmployeeWithImage(EmployeeDTO dto, MultipartFile image) throws IOException;

	List<EmployeeDTO> getEmployeesByInTime(LocalDateTime inTime);
}
