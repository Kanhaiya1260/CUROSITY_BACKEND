package com.app.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import org.springframework.format.annotation.DateTimeFormat;

import com.app.entities.EmploymentType;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class EmployeeDTO {
	@JsonProperty(access = Access.READ_ONLY) // used during serialization
	private Long id;
	@NotBlank
	private String firstName;
	@NotBlank
	private String lastName;
	@Email
	private String email;
	@JsonProperty(access = Access.WRITE_ONLY) //required only in de-ser.
	private String password;
	@JsonProperty(access = Access.WRITE_ONLY)
	private String confirmPassword;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate joinDate;
	@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") //added new | can be replaced by @JsonFormat with the same pattern : tested that also
	private LocalDateTime inTime;
	//@JsonFormat(pattern ="HH:mm:ss",shape =JsonFormat.Shape.STRING)
	@DateTimeFormat(pattern ="HH:mm:ss")
	private LocalTime outTime;
	private EmploymentType type;
	private double salary;
	@JsonProperty(access = Access.WRITE_ONLY) // used during de-serialization
	private Long deptId;
}
