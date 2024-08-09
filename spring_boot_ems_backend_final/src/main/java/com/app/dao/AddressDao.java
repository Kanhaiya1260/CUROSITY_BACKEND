package com.app.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.app.entities.Address;
import com.app.entities.Employee;

public interface AddressDao extends JpaRepository<Address, Long> {
	//list all emps by specified city (eg of custom query)
	//if you give wrong property name instead of a.owner : it will b detected as run time err
		@Query("select a.owner from Address a where a.city=:city")
		List<Employee> findEmployeesByCity(String city);
		//delete all addresses from specific state , rets no of rows
		long deleteByState(String state);//will cause select + n delete queries
		//OR : bulk deletion approach
		@Query("delete from Address a where a.state=:state")
		@Modifying
		long deleteAddressDetailsByState(String state);
}
