package com.app.dao;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import com.app.entities.Employee;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
class AddressDaoTest {
	@Autowired
	private AddressDao dao;

	@Test
	void testFindEmployeesByCity() {
		List<Employee> list = dao.findEmployeesByCity("Pune");
		list.forEach(System.out::println);
	}
	
	@Test
	void testDeleteByCity() {
	//	long count=dao.deleteByState("MH"); OR 
		long count=dao.deleteAddressDetailsByState("MH");
		System.out.println(count);
	}

}
