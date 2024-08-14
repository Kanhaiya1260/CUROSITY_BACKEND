package com.app.dto;

import java.time.LocalDate;
import java.util.List;

import com.app.Entities.Product;
import com.app.Entities.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProductDescDTO {
	
	private Product prod;
	private List<Long> imgIds;
	private LocalDate date;
	private List<Size> sizes;
	private int rating;
	
}
