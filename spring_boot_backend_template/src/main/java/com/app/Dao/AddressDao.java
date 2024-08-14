package com.app.Dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.app.Entities.Address;

public interface AddressDao extends JpaRepository<Address,Long>{
	
	@Query("Select a from Address a where a.user.uid = :id")
    List<Address> findAllUserAddressById(@Param("id")Long id);
}
