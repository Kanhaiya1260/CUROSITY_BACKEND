package com.app.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.validation.constraints.NotEmpty;

import com.app.Entities.Address;
import com.app.Entities.Product;
import com.app.Entities.ProductVariant;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class OrderResponseDto {
	private Long orderId;
	private LocalDate orderDate;
	private LocalDateTime delhiveryDate;
	private Integer quantity;
    private boolean status;
    private Address address;
    private ProductVariantDTO productDto; 
    private String phoneNo;
    private String userName;
}