package com.app.dto;

import com.app.Entities.Category;
import com.app.Entities.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ProdFilterReqDTO {
	private Size size;
	private String color;
	private Category category;
	private int[] price;
}
