package com.app.Services;

import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.app.Dao.UserDao;
import com.app.Entities.Address;
import com.app.Entities.ProductVariant;
import com.app.Entities.User;
import com.app.customException.ResourceNotFoundException;
import com.app.dto.UserRegisterDTO;
import com.app.dto.UserResponseDto;
import com.app.dto.ApiResponse;
import com.app.dto.UserLoginDTO;

@Service
@Transactional
public class UserServiceImpl implements UserService{
	
	@Autowired
	private UserDao dao;
	
	@Autowired
	ModelMapper mapper;

	@Override
	public String register(UserRegisterDTO user) {
 		User newUser = mapper.map(user,User.class); 
		dao.save(newUser);
		return "registered successfully!";
	}

	@Override
	public UserResponseDto login(UserLoginDTO u) {
		User user = dao.findByEmail(u.getEmail()).orElseThrow(()->new RuntimeException("user not found"));
		if(user.getPassword().equals(u.getPassword())) {
			UserResponseDto resultUser = mapper.map(user,UserResponseDto.class);
			return resultUser;
		}
		else {
			throw new RuntimeException("invalid credentials!");
		}
	}

	@Override
	public ApiResponse updateUserDetails(UserResponseDto user) {
		// TODO Auto-generated method stub
		Long uid = user.getId();
		User currentUser = dao.findById(uid).orElseThrow(()-> new ResourceNotFoundException("User Not FOund"));
		System.out.println(user);
		System.out.println(user.getDOB());
		currentUser.setDOB(user.getDOB());
		currentUser.setEmail(user.getEmail());
		currentUser.setFirstName(user.getFirstName());
		currentUser.setLastName(user.getLastName());
		currentUser.setRole(user.getRole());
        dao.save(currentUser);
		return new ApiResponse("Updated User Details");
	}
}
