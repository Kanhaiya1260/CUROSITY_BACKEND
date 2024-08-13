package com.app.Services;

import java.util.List;

import com.app.Entities.Address;
import com.app.Entities.User;
import com.app.dto.UserRegisterDTO;
import com.app.dto.UserResponseDto;
import com.app.dto.ApiResponse;
import com.app.dto.UserLoginDTO;

public interface UserService {
	public String register(UserRegisterDTO user);
	public UserResponseDto login(UserLoginDTO user);
	public List<Address> FindUserAddress(Long Id);
	public ApiResponse UpdatePassWord(String newPassWord,String LastPassWord);
}
