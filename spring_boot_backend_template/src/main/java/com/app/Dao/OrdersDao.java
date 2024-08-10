package com.app.Dao;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.app.Entities.Orders;
import com.app.dto.TrendingOrderDTO;

public interface OrdersDao extends JpaRepository<Orders,Long> {
	@Query("SELECT new com.app.dto.TrendingOrderDTO(o.product.pid, SUM(o.quantity)) " +
		       "FROM Orders o " +
		       "WHERE o.orderDate BETWEEN :startDate AND :endDate " +
		       "GROUP BY o.product.pid")
	List<TrendingOrderDTO> findOrdersInDateRange(@Param("startDate")LocalDate startDate,@Param("endDate")LocalDate endDate);
}
