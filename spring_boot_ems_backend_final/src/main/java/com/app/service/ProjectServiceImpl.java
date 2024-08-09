package com.app.service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.custom_exceptions.ResourceNotFoundException;
import com.app.dao.EmployeeDao;
import com.app.dao.ProjectDao;
import com.app.dao.ProjectEmployeeDetailsDao;
import com.app.dto.ApiResponse;
import com.app.dto.ProjectDTO;
import com.app.entities.Employee;
import com.app.entities.Project;
import com.app.entities.ProjectEmployeeDetails;
import com.app.entities.ProjectEmployeeId;

@Service
@Transactional
public class ProjectServiceImpl implements ProjectService {
	@Autowired
	private ProjectDao projectRepo;

	@Autowired
	private EmployeeDao empRepo;

	@Autowired
	private ModelMapper mapper;

	@Autowired
	private ProjectEmployeeDetailsDao projectEmpRepo;

	@Override
	public ProjectDTO launchNewProject(ProjectDTO dto) {
		// map dto --> entity
		Project project = mapper.map(dto, Project.class);
		// project.setStatus(ProjectStatus.LAUNCHED);
		Project savedProject = projectRepo.save(project);
		return mapper.map(savedProject, ProjectDTO.class);
	}

	@Override
	public List<ProjectDTO> getAllProjects() {

		return projectRepo.findAll()
				.stream()
				.map(project -> mapper.map(project, ProjectDTO.class))
				.collect(Collectors.toList());
	}

	@Override
	public ApiResponse assignEmpToProject(Long projectId, Long empId) {
		Project project = projectRepo.getReferenceById(projectId);
//				.findById(projectId)
//				.orElseThrow(() -> new ResourceNotFoundException("Invalid Project ID!!!!"));
		Employee emp = empRepo.getReferenceById(empId);

		// .findById(empId).orElseThrow(() -> new ResourceNotFoundException("Invalid Emp
		// ID!!!!"));

		// Not needed ! ProjectEmployeeId id=new ProjectEmployeeId(projectId, empId);
		ProjectEmployeeDetails details = new ProjectEmployeeDetails();
		details.setMyEmployee(emp); //establishing many-to-one uni dir asso : projempdetails --> emp
		details.setMyProject(project);//establishing many-to-one uni dir asso : projempdetails --> project
		projectEmpRepo.save(details);
		return new ApiResponse("Emp  added to Project : ");
	}//rec will be inserted in asso table

	@Override
	public ApiResponse removeEmpFromProject(Long projectId, Long empId) {

		// Currently not added any  validations to chk if projectId n emp Id : exists
		// You can add them (refer to earlier method)
		ProjectEmployeeId id = new ProjectEmployeeId(projectId, empId);
		projectEmpRepo.deleteById(id);
		return new ApiResponse("delete emp details from specified project");
	}

	@Override
	public ProjectDTO updateProject(Long projectId, @Valid ProjectDTO dto) {
		// validate if project exists by id
		Project project = projectRepo.findById(projectId)
				.orElseThrow(() -> new ResourceNotFoundException("Invalid Project Id !!!"));
		// => project exists
		// dto contains the updates , so apply it --> to entity
		mapper.map(dto, project);
		System.out.println("after mapping " + projectId);
		projectRepo.save(project);
		dto.setId(projectId);// so that it doesn't send null in the json resp
		return dto;
	}

	@Override
	public ApiResponse deleteProject(Long projectId) {
		// delete child records in from child table (entity : ProjectEmp)
		long noOfEmpsInProject = projectEmpRepo.deleteByMyProjectId(projectId);
		System.out.println("deleted " + noOfEmpsInProject);
		// delete project details : currently assuming projectId exists , later you can add
		// a check
		projectRepo.deleteById(projectId);
		return new ApiResponse("Deleted project details ....");
	}

	@Override
	public ApiResponse updateEmpPerformanceDetails(Long projectId, Long empId, double perfIndex) {
		ProjectEmployeeId id = new ProjectEmployeeId(projectId, empId);
		ProjectEmployeeDetails details = projectEmpRepo.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Emp id or project id invalid !!!"));
		details.setEmpPerformanceIndex(perfIndex);
		return new ApiResponse("Updated performance index for specific emp for specific project");

	}

	@Override
	public ProjectDTO patchProjectDetails(Long projectId, Map<String, Object> map) {
		// chk if project exists
				Project project= projectRepo.findById(projectId)
						.orElseThrow(() -> new ResourceNotFoundException("Invalid Project Id!!!!"));
		
				mapper.map(map,project);
					System.out.println("updated project " + projectId);
				return mapper.map(project, ProjectDTO.class);
	}
	
	

}
