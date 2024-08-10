package com.app.dto;

import java.time.LocalDate;

import java.time.LocalDateTime;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Null;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrdersDTO {
	private Long orderId;
//    private LocalDate orderDate = LocalDate.now();
//    private LocalDateTime deliveryDate = LocalDateTime.now().plusDays(7);
    @NotEmpty
	private Integer quantity;
    private boolean status;
    @NotEmpty
    private Long addressId;
    @NotEmpty
    private Long productId;
    @NotEmpty
    private Long userId;
}
