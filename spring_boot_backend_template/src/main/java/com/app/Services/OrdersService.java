package com.app.Services;

import com.app.Entities.Orders;
import com.app.dto.ApiResponse;
import com.app.dto.OrdersDto;

public interface OrdersService {
    public ApiResponse placeOrder(OrdersDto order);
    public ApiResponse deleteOrder(Long OrderId);
	public ApiResponse updateStatus(Long OrderId);
	//public ApiResponse findTrendingLastMonth();
}
