package com.app.dto;

import java.util.List;

import com.app.Entities.Category;
import com.app.Entities.Product;
import com.app.Entities.ProductVariant;
import com.app.Entities.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductReqDTO {
	
	Product product;
    private List<ProductVariantDTO> variants; 
}
