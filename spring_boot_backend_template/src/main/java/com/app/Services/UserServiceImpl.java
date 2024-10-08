package com.app.Services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.app.Dao.ProductDao;
import com.app.Dao.ProductVariantDao;
import com.app.Dao.UserDao;
import com.app.Dao.WishListDao;
import com.app.Entities.Address;
import com.app.Entities.Product;
import com.app.Entities.ProductVariant;
import com.app.Entities.User;
import com.app.Entities.WishList;
import com.app.customException.ResourceNotFoundException;
import com.app.dto.UserRegisterDTO;
import com.app.dto.UserResponseDto;
import com.app.dto.WishListDTO;
import com.app.dto.ApiResponse;
import com.app.dto.ProductDescDTO;
import com.app.dto.UserLoginDTO;

@Service
@Transactional
public class UserServiceImpl implements UserService{
	
	@Autowired
	private UserDao dao;
	
	@Autowired
	ModelMapper mapper;
	
	@Autowired
	WishListDao wishlistdao;
	
	@Autowired
	ProductDao prodDao;
	
	@Autowired 
    private ProductVariantDao variant;
	
	private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);
	
	@Override
	public String register(UserRegisterDTO user) {
 		User newUser = mapper.map(user,User.class); 
 		newUser.setPassword(encoder.encode(newUser.getPassword()));
		dao.save(newUser);
		return "registered successfully!";
	}

	@Override
	public UserResponseDto login(UserLoginDTO u) {
		User user = dao.findByEmail(u.getEmail()).orElseThrow(()->new RuntimeException("user not found"));
		UserResponseDto resultUser = mapper.map(user,UserResponseDto.class);
		return resultUser;
	}

	@Override
	public UserResponseDto updateUserDetails(UserResponseDto user) {
		// TODO Auto-generated method stub
		Long uid = user.getUid();
		User currentUser = dao.findById(uid).orElseThrow(()-> new ResourceNotFoundException("User Not FOund"));
		currentUser.setDOB(user.getDOB());
		currentUser.setEmail(user.getEmail());
		currentUser.setFirstName(user.getFirstName());
		currentUser.setLastName(user.getLastName());
		currentUser.setPhone(user.getPhone());
		UserResponseDto response = mapper.map(currentUser,UserResponseDto.class);
        dao.save(currentUser);
        return response;
	}

	@Override
	public String addToWishList(Long imgid,Long uid){
		
		WishList wsh = new WishList();
		wsh.setImgid(imgid);
		wsh.setUid(uid);
		wishlistdao.save(wsh);
		return "product added ot wishlist";
	}

	@Override
	public List<WishListDTO> getWishItems(Long uid) {
		List<WishListDTO> items = new ArrayList<WishListDTO>();
		List<WishList> wshitems = wishlistdao.findByuid(uid);
		System.out.println(wshitems);
		for(WishList wsh:wshitems) {
			WishListDTO item  = new WishListDTO();
        	item.setImgid(wsh.getImgid());
            ProductVariant prodvar = variant.findById(wsh.getImgid()).orElseThrow(() -> new ResourceNotFoundException("Product Doesn't Exist"));
            Product product = prodvar.getProduct();
        	item.setPrice(product.getPrice());
        	item.setTitle(product.getProductName());
        	item.setRating(prodvar.getRating());
        	items.add(item);
        }
		return items;
	}

	@Override
	public ApiResponse deleteFromWishList(Long imgid,Long uid) {
		 
		int cnt = wishlistdao.deleteByImgIdAndUid(imgid, uid);
		if(cnt>0)
		return new ApiResponse("product deleted from wishlist");
		else
			return new ApiResponse("failed");
	}
}
