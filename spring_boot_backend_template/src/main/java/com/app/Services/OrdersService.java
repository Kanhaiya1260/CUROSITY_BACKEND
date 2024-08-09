package com.app.Services;

import java.util.List;

import com.app.Entities.Orders;
import com.app.Entities.Product;
import com.app.dto.ApiResponse;
import com.app.dto.OrdersDTO;

public interface OrdersService {
    public ApiResponse placeOrder(OrdersDTO order);
    public ApiResponse deleteOrder(Long OrderId);
	public ApiResponse updateStatus(Long OrderId);
	public List<Product> getTrendingProducts();
}