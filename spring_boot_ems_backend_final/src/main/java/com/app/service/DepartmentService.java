package com.app.service;

import java.util.List;

import javax.validation.Valid;

import com.app.dto.ApiResponse;
import com.app.dto.DepartmentDTO;
import com.app.dto.DepartmentEmpsDTO;

public interface DepartmentService {
	List<DepartmentDTO> getAllDepartmens();
	DepartmentDTO getDepartmentDetails(Long deptId);
	DepartmentEmpsDTO getDepartmentAndEmpDetails(Long deptId);
	DepartmentDTO addNewDepartment(DepartmentDTO dept);
	DepartmentDTO updateDepartment(Long deptId, DepartmentDTO dept);
}
