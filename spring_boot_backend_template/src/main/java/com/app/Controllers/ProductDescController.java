package com.app.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.app.Services.ProductDescService;
import com.app.dto.ApiResponse;
import com.app.dto.ProductDescDTO;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/productDetails")
@Validated
public class ProductDescController {
	
	@Autowired
	ProductDescService pas;
	
	@GetMapping()
	public ResponseEntity<?> getProductDetails(@RequestParam(required = false) Long imgid) {
		try {
			ProductDescDTO products = pas.getProductDetails(imgid);
	    		return ResponseEntity.ok(products);
	}catch (Exception e) {
		return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(new ApiResponse(e.getMessage()));
	}	
	}}

