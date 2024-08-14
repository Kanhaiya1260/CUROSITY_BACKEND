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
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import com.app.Dao.AddressDao;
import com.app.Dao.OrdersDao;
import com.app.Dao.ProductDao;
import com.app.Dao.ProductVariantDao;
import com.app.Dao.UserDao;
import com.app.Entities.Address;
import com.app.Entities.Orders;
import com.app.Entities.Product;
import com.app.Entities.ProductVariant;
import com.app.Entities.User;
import com.app.customException.ResourceNotFoundException;
import com.app.dto.ApiResponse;
import com.app.dto.OrderResponseDto;
import com.app.dto.OrdersDTO;
import com.app.dto.ProductVariantDTO;
import com.app.dto.TrendingOrderDTO;

@Service
@Transactional
public class OrderServiceImpl implements OrdersService {
    
	@Autowired
	private OrdersDao orderDao;
	
	@Autowired
	private ModelMapper mapper;
	
	@Autowired
	private ProductVariantDao productDao;
	
	@Autowired 
	private AddressDao addressDao;
	
	@Autowired
	private UserDao userDao;
	
	@Override
	public ApiResponse placeOrder(OrdersDTO order) {
		// TODO Auto-generated method stub
		//finding Relations through their Id's
		ProductVariant product = productDao.findById(order.getProductVarientId())
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
		product.setStock(product.getStock()-order.getQuantity());
		productDao.save(product);
		orderDao.save(currentOrder);
		
		return new ApiResponse("Added Order SuccessFully!");
	}

	@Override
	public ApiResponse deleteOrder(Long OrderId) {
		// TODO Auto-generated method stub
		Orders currentOrder = orderDao.findById(OrderId)
				.orElseThrow(() -> new ResourceNotFoundException("Order Dosn't Exists"));
		if(currentOrder.isStatus() == false) {
			ProductVariant currentProduct = currentOrder.getProduct();
			currentProduct.setStock(currentProduct.getStock()+1);
			productDao.save(currentProduct);
			orderDao.deleteById(OrderId);
			return new ApiResponse("Order Deleted SuccessFully");
		}
		return new ApiResponse("Order Is Delivered");
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
	public List<ProductVariantDTO> getTrendingProducts() {
		 
		List<TrendingOrderDTO> listOfOrders = orderDao.findOrdersInDateRange(LocalDate.now().minusDays(7),LocalDate.now());
		
		listOfOrders =  listOfOrders.stream()
				.sorted((o1,o2) -> o2.getTotalQuantity().compareTo(o1.getTotalQuantity()))
				.limit(6)
				.collect(Collectors.toList());
		List<ProductVariantDTO> result = new ArrayList<>();
		for(TrendingOrderDTO order : listOfOrders) {
			ProductVariant currentProduct = productDao.findById(order.getProductId())
					.orElseThrow(()-> new ResourceNotFoundException("Product Not Found"));
			    System.out.println("hello");
				result.add(mapper.map(currentProduct,ProductVariantDTO.class));	
		}
		return result;
	}
	@Override
	public List<OrderResponseDto> UserOrders(Long Id){
		
		List<Orders> userOrders = orderDao.findOrdersById(Id);
		List<OrderResponseDto> newUserOrders = new ArrayList<OrderResponseDto>();
		for(Orders o : userOrders ) {
			ProductVariant currentProduct = o.getProduct();
			OrderResponseDto currentOrder = mapper.map(o,OrderResponseDto.class);
			ProductVariantDTO currDto =  mapper.map(currentProduct,ProductVariantDTO.class);
			currentOrder.setAddress(o.getDelhiveryAddress());
			currentOrder.setProductDto(currDto);
			newUserOrders.add(currentOrder);
		}
		return newUserOrders;
	}
}
