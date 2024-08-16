package com.app.Controllers;

import java.util.List;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.app.Entities.Address;
import com.app.Entities.WishList;
import com.app.Services.AddressService;
import com.app.Services.UserService;
import com.app.dto.UserRegisterDTO;
import com.app.dto.UserResponseDto;

import ch.qos.logback.core.status.Status;

import com.app.dto.AddressDTO;
import com.app.dto.ApiResponse;
import com.app.dto.UserLoginDTO;


@RestController
@RequestMapping
@Validated
@CrossOrigin(origins = "http://localhost:5173")
public class UserController {
	
	@Autowired
	private UserService serv;
	
	@Autowired
	private AddressService addressService;
	
	@PostMapping("/register")
	public ResponseEntity<?> register(@RequestBody @Valid UserRegisterDTO u){
		try {
			return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse("success",serv.register(u)));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(e.getMessage());
		}
	}
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody @Valid UserLoginDTO u){
		try {
			return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<UserResponseDto>("success","SuccessFully Logged In",serv.login(u)));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(new ApiResponse(e.getMessage()));
		}
	}
	
	
	@PostMapping("/addAddress")
	public ResponseEntity<?> addAddress(@Valid @RequestBody AddressDTO newAddress){
		System.out.println("Hello");
		try{
		   return ResponseEntity.status(HttpStatus.CREATED).body(addressService.addUserAddress(newAddress));
		}catch(Exception e){
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage()));
		}
	}
	
	@PostMapping("/addToWishList")
	public ResponseEntity<?> addtowishlist(@Valid @RequestParam("imgid") Long imgid,@Valid
		    @RequestParam("uid") Long uid ){
		try{
		   return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse(serv.addToWishList(imgid,uid)));
		}catch(Exception e){
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage()));
		}
	}
	
	@GetMapping("/getWishList")
	public ResponseEntity<?> getwishlist(@Valid
		    @RequestParam("uid") Long uid ){
		try{
		   return ResponseEntity.status(HttpStatus.CREATED).body(serv.getWishItems(uid));
		}catch(Exception e){
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage()));
		}
	}
	@DeleteMapping("/delete/{imgid}")
    public ResponseEntity<?> deleteProduct(@RequestParam Long imgid,@RequestParam Long uid){
		return ResponseEntity.ok(serv.deleteFromWishList(imgid,uid));
    }
	
	@GetMapping("/address") 
	public ResponseEntity<?> getUserAddress(@RequestParam Long uid){
		
		List<Address> allAddress = addressService.GetUserAddress(uid);
		if(allAddress == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("User Not Found"));
		}
	    return ResponseEntity.ok(allAddress);
	}
	
	@PostMapping("/update")
	public ResponseEntity<?>updateUserDetails(@RequestBody UserResponseDto user){
		try {
			return ResponseEntity.status(HttpStatus.OK).body(serv.updateUserDetails(user));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(new ApiResponse(e.getMessage()));
		} 
	}
}
