package com.app.Services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.criteria.Order;
import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.Dao.AddressDao;
import com.app.Dao.OrdersDao;
import com.app.Dao.ProductDao;
import com.app.Dao.UserDao;
import com.app.Entities.Address;
import com.app.Entities.Orders;
import com.app.Entities.Product;
import com.app.Entities.User;
import com.app.customException.ResourceNotFoundException;
import com.app.dto.ApiResponse;
import com.app.dto.OrdersDTO;
import com.app.dto.TrendingOrderDTO;

@Service
@Transactional
public class OrderServiceImpl implements OrdersService {
    
	@Autowired
	private OrdersDao orderDao;
	
	@Autowired
	private ModelMapper mapper;
	
	@Autowired
	private ProductDao productDao;
	
	@Autowired 
	private AddressDao addressDao;
	
	@Autowired
	private UserDao userDao;
	
	@Override
	public ApiResponse placeOrder(OrdersDTO order) {
		// TODO Auto-generated method stub
		//finding Relations through their Id's
		Product product = productDao.findById(order.getProductId())
				.orElseThrow(() -> new ResourceNotFoundException("Product Not Found!"));
		Address address = addressDao.findById(order.getAddressId())
				.orElseThrow(() -> new ResourceNotFoundException("Address Not Exist!"));
		User user = userDao.findById(order.getUserId())
				.orElseThrow(() -> new ResourceNotFoundException("User Dosn't Exist!"));
		
		//Mapping Orders Dto to Orders Class
		Orders currentOrder = mapper.map(order,Orders.class);
		
		//setting Orders felids
		currentOrder.setProduct(product);
		currentOrder.setDelhiveryAddress(address);
		currentOrder.setUser(user);
		
		System.out.println(currentOrder);
		orderDao.save(currentOrder);
		
		return new ApiResponse("Added Order SuccessFully!");
	}

	@Override
	public ApiResponse deleteOrder(Long OrderId) {
		// TODO Auto-generated method stub
		Orders currentOrder = orderDao.findById(OrderId)
				.orElseThrow(() -> new ResourceNotFoundException("Order Dosn't Exists"));
		if(currentOrder.isStatus() == false) {
			orderDao.deleteById(OrderId);
			return new ApiResponse("Order Deleted SuccessFully");
		}
		return new ApiResponse("invalid Operation");
	}

	@Override
	public ApiResponse updateStatus(Long OrderId) {
		// TODO Auto-generated method stub
		Orders currentOrder = orderDao.findById(OrderId)
				.orElseThrow(() -> new ResourceNotFoundException("Order Dosn't Exists"));
		if(currentOrder.isStatus() == false) {
		    currentOrder.setDelhiveryDate(LocalDateTime.now());
		    currentOrder.setStatus(true);
			return new ApiResponse("Order Updated SuccessFully");
		}	
		return new ApiResponse("invalid Operation");
	}

	@Override
	public List<Product> getTrendingProducts() {
		 
		List<TrendingOrderDTO> listOfOrders = orderDao.findOrdersInDateRange(LocalDate.now().minusDays(7),LocalDate.now());
		
		listOfOrders =  listOfOrders.stream()
				.sorted((o1,o2) -> o2.getTotalQuantity().compareTo(o1.getTotalQuantity()))
				.limit(6)
				.collect(Collectors.toList());
		List<Product> result = new ArrayList<>();
		for(TrendingOrderDTO order : listOfOrders) {
			Product currentProduct = productDao.findById(order.getProductId())
					.orElseThrow(()-> new ResourceNotFoundException("Product Not Found"));
				result.add(currentProduct);	
		}
		return result;
	}
}
