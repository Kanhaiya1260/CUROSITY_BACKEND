package com.app.dto;

import java.time.LocalDate;

import java.time.LocalDateTime;

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
    private LocalDate orderDate = LocalDate.now();
    private LocalDateTime deliveryDate = LocalDateTime.now().plusDays(7);
    private Integer quantity;
    private boolean status = false;
    private Long addressId;
    private Long productId;
    private Long userId;
}
