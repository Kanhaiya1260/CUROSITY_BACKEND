package com.app.Services;

import java.util.List;

import javax.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.app.Dao.UserDao;
import com.app.Entities.Address;
import com.app.Entities.User;
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
	public List<Address> FindUserAddress(Long Id) {
		// TODO Auto-generated method stub

		return null;
	}

	@Override
	public ApiResponse UpdatePassWord(String newPassWord, String LastPassWord) {
		// TODO Auto-generated method stub
		return null;
	}
	

	
	
	
}
