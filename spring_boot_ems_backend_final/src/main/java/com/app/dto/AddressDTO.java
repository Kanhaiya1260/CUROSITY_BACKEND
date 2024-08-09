package com.app.dto;

import java.time.LocalDate;

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
public class AddressDTO {
	
	private String street;
	
	private String city;
	
	private String state;
	
	private String country;
	
	private String zipCode;
	private LocalDate someDate;
	
		
}
