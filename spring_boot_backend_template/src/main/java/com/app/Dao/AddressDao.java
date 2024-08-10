package com.app.Dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.Entities.Address;

public interface AddressDao extends JpaRepository<Address,Long>{

}
