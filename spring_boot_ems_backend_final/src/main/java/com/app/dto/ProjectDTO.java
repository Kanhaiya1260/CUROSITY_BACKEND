package com.app.dto;

import java.time.LocalDate;

import javax.validation.constraints.NotBlank;

import com.app.entities.BaseEntity;
import com.app.entities.ProjectStatus;
import com.app.validations.StartDateBeforeEnd;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@StartDateBeforeEnd
public class ProjectDTO {
	@JsonProperty(access = Access.READ_ONLY)
	private Long id;
	@NotBlank
	private String title;
	private LocalDate startDate;	
	private LocalDate endDate;
//	@JsonProperty(access = Access.READ_ONLY)
	private ProjectStatus status;
	private double budget;

}
