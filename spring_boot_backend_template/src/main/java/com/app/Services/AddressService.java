package com.app.Services;

import java.util.List;

import com.app.Entities.Address;
import com.app.dto.AddressDTO;
import com.app.dto.ApiResponse;

public interface AddressService {
    public ApiResponse addUserAddress(AddressDTO address);
    public List<Address> GetUserAddress(Long uid);
}
