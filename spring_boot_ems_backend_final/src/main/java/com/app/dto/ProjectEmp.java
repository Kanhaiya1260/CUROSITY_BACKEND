package com.app.dto;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ProjectEmp {
	@NotNull
	private Long projectId;
	@NotNull
	private Long employeeId;
	private double performanceIndex;
}
