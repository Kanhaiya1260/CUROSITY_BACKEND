package com.app.controller;

import static org.springframework.http.MediaType.IMAGE_GIF_VALUE;
import static org.springframework.http.MediaType.IMAGE_JPEG_VALUE;
import static org.springframework.http.MediaType.IMAGE_PNG_VALUE;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.app.dto.EmployeeDTO;
import com.app.service.EmployeeService;
import com.app.service.ImageHandlingService;

@RestController
@RequestMapping("/employees")
@CrossOrigin(origins = "http://localhost:3000")
public class EmployeeController {
	@Autowired
	private EmployeeService employeeService;

	@Autowired
	private ImageHandlingService imageService;

	// 1. add new emp to existing dept
	// http://host:port/employees , method=POST
	@PostMapping
	public ResponseEntity<?> addNewEmployee(@RequestBody @Valid EmployeeDTO dto) {
		System.out.println("in add emp " + dto);
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(employeeService.addNewEmployee(dto));
	}

	// 2. http://host:port/employees/departments/{deptId} , method=GET
	// get all emp details for specific dept
	@GetMapping(value = "/departments/{deptId}")
	public ResponseEntity<?> getEmpDetailsByDepartment(@PathVariable Long deptId) throws IOException {
		System.out.println("get emps by dept " + deptId);
		return ResponseEntity.ok(employeeService.getAllEmployeesFromDept(deptId));
	}

	// http://host:port/employees/departments/{deptId}/{empId}
	// 3. add req handling method(API end point) to get emp details by emp id n dept
	// id
	@GetMapping("/departments/{deptId}/{empId}")
	// dept id is here just used for validation

	public ResponseEntity<?> getEmpDetailsByDeptAndEmpId(@PathVariable Long deptId, @PathVariable Long empId) {
		System.out.println("in get emp details by dept id n emp id " + deptId + " " + empId);
		return ResponseEntity.ok(employeeService.getEmpDetails(deptId, empId));
	}

	// 4. update emp details
	// http://host:port/employees/{empId} , method=PUT
	@PutMapping("/{empId}")
	public ResponseEntity<?> updateEmployee(@PathVariable Long empId, @RequestBody @Valid EmployeeDTO dto) {
		System.out.println("in update emp " + empId + " " + dto);
		return ResponseEntity.ok(employeeService.updateEmployee(empId, dto));
	}

	// 5. delete emp details
	// http://host:port/employees/{empId} , method=DELETE
	@DeleteMapping("/{empId}")
	public ResponseEntity<?> deleteEmployee(@PathVariable Long empId) {
		System.out.println("in update emp " + empId);
		return ResponseEntity.ok(employeeService.deleteEmpDetails(empId));
	}

	// 6. Upload image for existing emp
	// http://host:port/employees/images/{empId} , method=POST
	@PostMapping(value = "/images/{empId}",
			consumes = "multipart/form-data")
	public ResponseEntity<?> uploadImage(@PathVariable 
			Long empId, 
			@RequestParam MultipartFile image)
			throws IOException {
		System.out.println("in upload image " + empId);
		return ResponseEntity.status(HttpStatus.CREATED).
				body(imageService.uploadImage(empId, image));
	}

//7. download image
	// http://host:port/employees/images/{empId} , method=GET
	@GetMapping(value = "/images/{empId}", 
			produces = 
		{ IMAGE_GIF_VALUE, IMAGE_JPEG_VALUE, IMAGE_PNG_VALUE })
	public ResponseEntity<?> downloadImage(@PathVariable long empId) throws IOException {
		System.out.println("in download image " + empId);
		return ResponseEntity.ok(imageService.serveImage(empId));
	}

	// 8. Pagination demo
	// Get all emps , paginated
	// http://host:port/employees , method=GET
	// req params : pageNumber , def val 0 , optional
	// pageSize : def val 3 , optional

	@GetMapping
	public ResponseEntity<?> getAllEmpsPaginated(
			@RequestParam(defaultValue = "0", required = false) int pageNumber,
			@RequestParam(defaultValue = "3", required = false) int pageSize) {
		System.out.println("in get all emps " + pageNumber + " " + pageSize);
		List<EmployeeDTO> list = employeeService.getAllEmployees(pageNumber, pageSize);
		if (list.isEmpty())
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		// emps found
		return ResponseEntity.ok(list);
	}

	// 9. Upload emp details along with image
	// http://host:port/employees/images , method=POST
	@PostMapping(value = "/emp_images", consumes = {
			MediaType.MULTIPART_FORM_DATA_VALUE/*
												 * , MediaType.APPLICATION_JSON_VALUE
												 */ })
	public ResponseEntity<?> uploadEmpAndImage(@RequestPart MultipartFile
			image, @RequestPart EmployeeDTO emp)
			throws IOException {
		System.out.println("in upload emp details n image " + emp + " " + image);	
		return ResponseEntity.
				status(HttpStatus.CREATED)
				.body(employeeService.addNewEmployeeWithImage(emp, image));
	}

	// URL : http://host:port/employees/in_time/{dateTime}
	// get emps with the same in time : 

	@GetMapping("/in_time/{inTime}")
	public ResponseEntity<?> getEmpsByInTime(@PathVariable
	@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") LocalDateTime inTime) {
		System.out.println("in time " + inTime);
		return ResponseEntity.ok().body(employeeService.getEmployeesByInTime(inTime));
	}

}
