package com.app.Services;

import java.time.LocalDate;
import java.util.List;

import com.app.Entities.Orders;
import com.app.Entities.Product;
import com.app.Entities.ProductVariant;
import com.app.dto.ApiResponse;
import com.app.dto.OrderResponseDto;
import com.app.dto.OrdersDTO;
import com.app.dto.ProductVariantDTO;

public interface OrdersService {
    public ApiResponse placeOrder(OrdersDTO order);
    public ApiResponse deleteOrder(Long OrderId);
	public ApiResponse updateStatus(Long OrderId);
	public List<ProductVariantDTO> getTrendingProducts();
	public List<OrderResponseDto> UserOrders(Long Id);
}
