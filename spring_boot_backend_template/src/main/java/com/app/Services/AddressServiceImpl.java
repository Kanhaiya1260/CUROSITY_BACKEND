package com.app.Services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.Dao.AddressDao;
import com.app.Dao.UserDao;
import com.app.Entities.Address;
import com.app.Entities.User;
import com.app.dto.AddressDTO;
import com.app.dto.ApiResponse;

@Service
@Transactional
public class AddressServiceImpl implements AddressService {

	@Autowired
	private AddressDao addressDao;
	
	@Autowired
	private ModelMapper mapper;
	
	@Autowired 
	private UserDao userDao;
	
	@Override
	public ApiResponse addUserAddress(AddressDTO address) {
		// TODO Auto-generated method stub
	    Optional<User> currentUserOpt = userDao.findById(address.getUid());
	    if(!currentUserOpt.isPresent()) {
	    	return new ApiResponse("User Not Found");
	    }
	    User currentUser = currentUserOpt.get();
		Address currentAddress = mapper.map(address,Address.class);
		currentUser.getAddress().add(currentAddress);
		currentAddress.setUser(currentUser);
		addressDao.save(currentAddress);
		return  new ApiResponse("Address Added SuccessFully");
	}

	@Override
	public List<Address> GetUserAddress(Long uid) {
		Optional<User> currentUserOpt = userDao.findById(uid);
		if(currentUserOpt.isPresent()) {
			 currentUserOpt.get().getAddress().size();
		     return currentUserOpt.get().getAddress();
		}
		return null;
	}
}
