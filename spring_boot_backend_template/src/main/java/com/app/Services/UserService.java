package com.app.Services;

import java.util.List;

import com.app.Entities.WishList;
import com.app.dto.UserRegisterDTO;
import com.app.dto.UserResponseDto;
import com.app.dto.WishListDTO;
import com.app.dto.ApiResponse;
import com.app.dto.ProductDescDTO;
import com.app.dto.UserLoginDTO;

public interface UserService {
	public String register(UserRegisterDTO user);
	public UserResponseDto login(UserLoginDTO user);
	public ApiResponse updateUserDetails(UserResponseDto  user);
	public String addToWishList(Long imgid,Long uid);
	public List<WishListDTO> getWishItems(Long uid);
	public ApiResponse deleteFromWishList(Long imgid,Long uid);
}
