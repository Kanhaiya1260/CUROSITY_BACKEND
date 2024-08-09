package com.app.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.app.Services.OrdersService;
import com.app.dto.ApiResponse;
import com.app.dto.OrdersDto;

@RestController
@RequestMapping("/orders")
public class OrdersController {
     @Autowired
     private OrdersService orderService;
     
     @PostMapping
     public ResponseEntity<?>placeNewOrder(@RequestBody OrdersDto newOrder){
    	 try {
    		return ResponseEntity.status(HttpStatus.CREATED)
    		.body(orderService.placeOrder(newOrder));
    	 }catch(RuntimeException e) {
    		 return ResponseEntity.status(HttpStatus.BAD_REQUEST)
    				 .body(new ApiResponse(e.getMessage()));
    	 }
     }
     @DeleteMapping("/{orderId}")
     public ResponseEntity<?>deleteOrder(@PathVariable Long orderId){
    	 try {
			return ResponseEntity.status(HttpStatus.ACCEPTED)
				.body(orderService.deleteOrder(orderId));
		} catch (Exception e) {
			// TODO: handle exception
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
   				 .body(new ApiResponse(e.getMessage()));
		} 
     }
     @PatchMapping("/{orderId}")
     public ResponseEntity<?>updateStatus(@PathVariable Long orderId){
    	 try {
 			return ResponseEntity.status(HttpStatus.ACCEPTED)
 				.body(orderService.updateStatus(orderId));
 		} catch (Exception e) {
 			// TODO: handle exception
 			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
    				 .body(new ApiResponse(e.getMessage()));
 		} 
     }
     
}
