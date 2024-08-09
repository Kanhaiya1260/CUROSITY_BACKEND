package com.app.controller;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.dto.DepartmentDTO;
import com.app.service.DepartmentService;

@RestController
@RequestMapping("/departments")
@Validated
public class DepartmentController {
	// dep
	@Autowired
	private DepartmentService departmentService;

	// add new department
	// http://host:port/departments
	@PostMapping
	public ResponseEntity<?> addNewDepartment(@RequestBody 
			@Valid DepartmentDTO dept) {
		System.out.println("in add dept " + dept);
		return ResponseEntity
				.status(HttpStatus.CREATED)
				.body(departmentService.addNewDepartment(dept));

	}

	// get department details
	// http://host:port/departments/{deptId}
	@GetMapping("/{deptId}")
	public ResponseEntity<?> getDepartmentDetails(@PathVariable Long deptId) {
		System.out.println("in get dept " + deptId);
		return ResponseEntity
				.ok(departmentService.getDepartmentDetails(deptId));

	}
	// get department n emp details
		// http://host:port/departments/{deptId}/emps , method=GET
		@GetMapping("/{deptId}/emps")
		public ResponseEntity<?> getDeptAndEmpDetails(
				@PathVariable @Min(1) @Max(10) Long deptId) {
			System.out.println("in get dept n emp dtls " + deptId);
			return ResponseEntity.ok(departmentService.
					getDepartmentAndEmpDetails(deptId));
		}
	// get all department details
		// http://host:port/departments
		@GetMapping
		public ResponseEntity<?> getAllDepartmentDetails() {
			System.out.println("in get all depts" );
			return ResponseEntity
					.ok(departmentService.getAllDepartmens());
		}
		// update existing  department
		// http://host:port/departments/{deptId}
		@PutMapping("/{deptId}")
		public ResponseEntity<?> updateDepartment(@PathVariable Long deptId,
				@RequestBody @Valid DepartmentDTO dept) {
			System.out.println("in update dept " +deptId+" "+ dept);		
			return ResponseEntity.
					ok(departmentService.updateDepartment(deptId , dept));
		}

}
