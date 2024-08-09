package com.app.dto;

import com.app.Entities.Category;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ProductReqDto {
    private String productName;
    private String productDesc;
    private Double price;
    private Category category; 
}
