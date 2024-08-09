package com.app.Dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.Entities.Orders;

public interface OrdersDao extends JpaRepository<Orders,Long> {
	
}
