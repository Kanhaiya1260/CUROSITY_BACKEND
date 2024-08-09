package com.app.service;

import java.time.LocalDate;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.modelmapper.AbstractConverter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.custom_exceptions.ResourceNotFoundException;
import com.app.dao.AddressDao;
import com.app.dao.EmployeeDao;
import com.app.dto.AddressDTO;
import com.app.dto.ApiResponse;
import com.app.entities.Address;
import com.app.entities.Employee;

@Service
@Transactional
public class AddressServiceImpl implements AddressService {
	@Autowired
	private EmployeeDao empRepo;

	@Autowired
	private AddressDao adrRepo;

	@Autowired
	private ModelMapper mapper;	

	@Override
	public AddressDTO getAddressDetails(Long addressId) {
		// TODO Auto-generated method stub
		return mapper.map(
				adrRepo.findById(addressId).orElseThrow(
						() -> new ResourceNotFoundException("Invalid Emp  Id Or Address not yet assigned !!!!")),
				AddressDTO.class);
	}

	@Override
	public ApiResponse assignEmpAddress(Long empId, AddressDTO address) {
		// validate emp : can be replaced by getReferenceById : a proxy
		Employee employee = empRepo.findById(empId)
				.orElseThrow(() -> new ResourceNotFoundException("Invalid Emp ID!!!"));
		// map dtp --> entity
		Address addressEntity = mapper.map(address, Address.class);
		// establish un dir link , adr ---> emp
		addressEntity.setOwner(employee);
		// save adr details
		adrRepo.save(addressEntity);
		return new ApiResponse("Assigned new address to Emp , " + employee.getFirstName());
	}

	@Override
	public ApiResponse updateEmpAddress(Long empId, AddressDTO address) {
		Address addressEntity = adrRepo.findById(empId)
				.orElseThrow(() -> new ResourceNotFoundException("Address is not yet assigned !!!! "));
	
		mapper.map(address, addressEntity);
		// save adr details
		adrRepo.save(addressEntity);
		return new ApiResponse("Updated address for  Emp ");

	}

	@Override
	public AddressDTO patchEmpAddress(Long empId, Map<String, Object> map) {
		// chk if adr exists
		Address address = adrRepo.findById(empId)
				.orElseThrow(() -> new ResourceNotFoundException("Invalid Address Id!!!!"));
			
		mapper.map(map,address);
		
		System.out.println("updated address " + address);
		return mapper.map(address, AddressDTO.class);
	}

}
