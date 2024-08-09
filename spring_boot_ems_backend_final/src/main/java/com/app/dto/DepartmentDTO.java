package com.app.dto;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class DepartmentDTO {
	@JsonProperty(access = Access.READ_ONLY) //used during serialization
	private Long id;
	@NotBlank
	private String deptName;
	@NotBlank
	private String location;
	
}
