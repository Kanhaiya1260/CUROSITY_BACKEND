package com.app.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.custom_exceptions.ResourceNotFoundException;
import com.app.dao.DepartmentDao;
import com.app.dto.DepartmentDTO;
import com.app.dto.DepartmentEmpsDTO;
import com.app.entities.Department;

@Service
@Transactional
public class DepartmentServiceImpl implements DepartmentService {
	// dep
	@Autowired
	private DepartmentDao departmentDao;

	@Autowired
	private ModelMapper mapper;

	@Override
	public List<DepartmentDTO> getAllDepartmens() {

		return departmentDao.findAll() // List<Department>
				.stream() // Stream<Department>
				.map(dept -> mapper.map(dept, DepartmentDTO.class)) // Stream<
				.collect(Collectors.toList());
	}

	@Override
	public DepartmentDTO getDepartmentDetails(Long deptId) {
		// get dept details --> map it to dto n return
		return mapper.map(
				departmentDao.findById(deptId).orElseThrow(() -> new ResourceNotFoundException("Invalid Dept Id !!!!")),
				DepartmentDTO.class);
	}

	@Override
	public DepartmentDTO addNewDepartment(DepartmentDTO dept) {
		Department departmentEntity = mapper.map(dept, Department.class);
		Department persistentDept = departmentDao.save(departmentEntity);
		return mapper.map(persistentDept, DepartmentDTO.class);
	}

	@Override
	public DepartmentEmpsDTO getDepartmentAndEmpDetails(Long deptId) {
		Department department = departmentDao.getDepartmentAndEmpDetails(deptId);
		return mapper.map(department, DepartmentEmpsDTO.class);
	}// dept +emps dto reted to the caller

	@Override
	public DepartmentDTO updateDepartment(Long deptId ,DepartmentDTO dept) {
		Department department = departmentDao.findById(deptId).
		orElseThrow(() -> new ResourceNotFoundException("Invalid Dept Id !!!!"));
		department.setDeptName(dept.getDeptName());
		department.setLocation(dept.getLocation());
		return mapper.map(department, DepartmentDTO.class);
	}

}
