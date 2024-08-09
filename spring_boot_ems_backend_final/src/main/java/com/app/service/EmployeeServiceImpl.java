package com.app.service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.app.custom_exceptions.ApiException;
import com.app.custom_exceptions.ResourceNotFoundException;
import com.app.dao.AddressDao;
import com.app.dao.DepartmentDao;
import com.app.dao.EmployeeDao;
import com.app.dao.ProjectEmployeeDetailsDao;
import com.app.dto.ApiResponse;
import com.app.dto.EmployeeDTO;
import com.app.entities.Address;
import com.app.entities.Department;
import com.app.entities.Employee;

@Service
@Transactional
public class EmployeeServiceImpl implements EmployeeService {
	// dep
	@Autowired
	private EmployeeDao empRepo;

	@Autowired
	private AddressDao adrRepo;

	@Autowired
	private DepartmentDao deptRepo;

	@Autowired
	private ModelMapper mapper;

	@Autowired
	private ImageHandlingService imgHandlingService;

	@Autowired
	private ProjectEmployeeDetailsDao projectEmpRepo;

	@Override
	public List<EmployeeDTO> getAllEmployeesFromDept(Long deptId) {
		List<Employee> empList = empRepo.findByDeptId(deptId);
		return empList.stream().map(emp -> mapper.map(emp, EmployeeDTO.class)).collect(Collectors.toList());
	}

	@Override
	public ApiResponse deleteEmpDetails(Long empId) {
		// checking if adr is assigned for this emp 
		Optional<Address> optionalAdr = adrRepo.findById(empId);
		if (optionalAdr.isPresent()) // child rec : adr exists ,so first delete that
			adrRepo.delete(optionalAdr.get());
		
		Employee emp = empRepo.findById(empId).
				orElseThrow(() -> new ResourceNotFoundException("Invalid emp id"));

		// Before deleting emp rec , delete it's child rec from ProjectEmpDetails
		projectEmpRepo.deleteByMyEmployeeId(empId);// yet to be tested
		empRepo.delete(emp);// yet to be tested
		return new ApiResponse("Emp Details of emp with ID " + emp.getId() + " deleted....");
	}

	@Override
	public EmployeeDTO addNewEmployee(EmployeeDTO dto) {

		// validate confirm password
		if (dto.getPassword().equals(dto.getConfirmPassword())) {
			// validate dept id
			Department dept = deptRepo.findById(dto.getDeptId())
					.orElseThrow(() -> new ResourceNotFoundException("Invalid Dept Id!!!"));
			Employee empEntity = mapper.map(dto, Employee.class);
			dept.addEmployee(empEntity);
			Employee savedEmp = empRepo.save(empEntity);
			System.out.println("emp entity id " + empEntity.getId() + " " + savedEmp.getId());
			return mapper.map(savedEmp, EmployeeDTO.class);			
		}
		throw new ApiException("Passwords don't match");

	}

	@Override
	public EmployeeDTO updateEmployee(Long empId, EmployeeDTO dto) {
		// validate if emp exists by id
		Employee emp = empRepo.findById(empId)
				.orElseThrow(() -> new ResourceNotFoundException("Invalid Emo ID , Emp not found !!!!"));
		// => emp exists
		// validate confirm password
		if (dto.getPassword().equals(dto.getConfirmPassword())) {
			// validate dept
			Department dept = deptRepo.findById(dto.getDeptId())
					.orElseThrow(() -> new ResourceNotFoundException("Invalid Dept Id!!!"));
			// dto contains the updates , so apply it --> to entity
			mapper.map(dto, emp);
			System.out.println("after mapping " + emp);
			dept.addEmployee(emp);		
			dto.setId(empId);// so that it doesn't send null in the json resp
			return dto;
		}
		throw new ApiException("Passwords don't match");

	}

	@Override
	public EmployeeDTO getEmpDetails(Long deptId, Long empId) {
		Employee employee = empRepo.findById(empId)
				.orElseThrow(() -> new ResourceNotFoundException("Invalid Emp ID!!!"));
		// chk if emp belongs to the specified dept : validation !
		if (employee.getDept().getId() != deptId)
			throw new ResourceNotFoundException("Dept id does not match !!!");

		return mapper.map(employee, EmployeeDTO.class);
	}

	@Override
	public List<EmployeeDTO> getAllEmployees(int pageNumber, int pageSize) {
		// Creates a PageRequest(imple class of Pageable : i/f for pagination)
		// based upon page no n size
		Pageable pageable = PageRequest.of(pageNumber, pageSize);
		// fetches the Page of Emps --> getContent() --> List<Emp>
		List<Employee> empList = empRepo.findAll(pageable).getContent();
		return empList.
				stream() //Stream<Emp>
				//Stream i/f method - map(Function mapperFunction)
				.map(emp -> mapper.map(emp, EmployeeDTO.class)) //Stream<dto>
				.collect(Collectors.toList());
	}

	@Override
	public EmployeeDTO addNewEmployeeWithImage(EmployeeDTO dto, MultipartFile image) throws IOException {

		// validate confirm password
		if (dto.getPassword().equals(dto.getConfirmPassword())) {
			// validate dept id
			Department dept = deptRepo.findById(dto.getDeptId())
					.orElseThrow(() -> new ResourceNotFoundException("Invalid Dept Id!!!"));
			Employee empEntity = mapper.map(dto, Employee.class);
			// upload image , set image path to emp.
			imgHandlingService.uploadImage(empEntity, image);
			dept.addEmployee(empEntity);
			Employee savedEmp = empRepo.save(empEntity);// Actually not needed by hibernate BUT to get persistent emp id
														// n to send to clnt doing this !
			// System.out.println("emp entity id " + empEntity.getId() + " " +
			// savedEmp.getId());

			return mapper.map(savedEmp, EmployeeDTO.class);

		}
		throw new ApiException("Passwords don't match");
	}

	@Override
	public List<EmployeeDTO> getEmployeesByInTime(LocalDateTime inTime) {
		// TODO Auto-generated method stub
		return empRepo.
				findByInTime(inTime)
				.stream()
				.map(emp -> mapper.map(emp,EmployeeDTO.class))
				.collect(Collectors.toList());
	}

}
