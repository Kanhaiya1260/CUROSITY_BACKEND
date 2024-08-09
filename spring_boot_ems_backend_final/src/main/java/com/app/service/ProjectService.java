package com.app.service;

import java.util.List;
import java.util.Map;

import javax.validation.constraints.NotNull;

import com.app.dto.ApiResponse;
import com.app.dto.ProjectDTO;

public interface ProjectService {
//add new project
	ProjectDTO launchNewProject(ProjectDTO newProject);

	List<ProjectDTO> getAllProjects();

	ApiResponse assignEmpToProject(Long projectId, Long empId);

	ApiResponse removeEmpFromProject(Long projectId, Long empId);

	ProjectDTO updateProject(Long projectId, ProjectDTO dto);

	ApiResponse deleteProject(Long projectId);
	// to do : update project status : if it's cancelled or completed , release all
	// emps

	// update perf index of emp in specific project
	ApiResponse updateEmpPerformanceDetails(Long projectId, Long empId, double perfIndex);

	ProjectDTO patchProjectDetails(Long projectId, Map<String, Object> map);

}
