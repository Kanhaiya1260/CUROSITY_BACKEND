package com.app.dto;

import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserLoginDTO {
	 @NotBlank(message="email cannot be blank")
	 private String email;
	 
	 @NotBlank(message="password cannot be blank")
	 private String password;
}
