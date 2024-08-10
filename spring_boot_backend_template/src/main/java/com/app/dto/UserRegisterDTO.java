package com.app.dto;

import java.time.LocalDate;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import com.app.Entities.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
 public class UserRegisterDTO {
	
 	@NotBlank(message = "firstname can't be blank")
	private String firstName;
 	
 	@NotBlank(message = "lastname can't be blank")
	private String lastName;
	
	@NotBlank(message = "email can't be blank")
  	private String email;
	
	@NotBlank(message = "phone can't be blank")
	private String phone;
	
	@NotBlank(message = "Password can't be blank")
	private String password;
	
	@NotNull(message = "role can't be blank")
	private Role role;
	
	@NotNull(message = "dob can't be blank")
	private LocalDate DOB;
	
}