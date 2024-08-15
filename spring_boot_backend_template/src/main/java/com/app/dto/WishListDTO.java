package com.app.dto;

import java.time.LocalDate;

import com.app.Entities.Role;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WishListDTO {

	private Long imgid;
	private Double price;
	private String title;
	private int rating;
}
