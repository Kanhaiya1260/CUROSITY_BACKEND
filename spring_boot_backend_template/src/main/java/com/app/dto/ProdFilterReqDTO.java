package com.app.dto;

import com.app.Entities.Category;
import com.app.Entities.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProdFilterReqDTO {
	private Size size;
	private String color;
	private Category cat;
	private int[] price;
}
