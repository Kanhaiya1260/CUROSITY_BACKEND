package com.app.dao;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import com.app.entities.Address;
import com.app.entities.AdhaarCard;
import com.app.entities.Department;
import com.app.entities.Employee;
import com.app.entities.EmploymentType;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
class EmployeeDaoTest {
	@Autowired
	private EmployeeDao dao;
	@Autowired
	private DepartmentDao deptDao;
	@Autowired
	private AddressDao adrDao;

	@Test
	void testSaveEmps() {
		/*
		 * firstName, String lastName, String email, String password, LocalDate
		 * joinDate, EmploymentType empType, double salary
		 */
		Department dept = deptDao.findById(1L).orElseThrow();
		List<Employee> list = List.of(
				new Employee("a1", "b1", "a1@gmail.com", "abc#12345", LocalDate.parse("2024-01-01"),
						EmploymentType.FULL_TIME, 345678),
				new Employee("a2", "b2", "a2@gmail.com", "def#2345", LocalDate.parse("2024-02-02"),
						EmploymentType.FULL_TIME, 445678),
				new Employee("a3", "b3", "a3@gmail.com", "xyz#1245", LocalDate.parse("2024-01-01"),
						EmploymentType.FULL_TIME, 345678));
		// establish bi dir asso bet dept n emps
		list.forEach(e -> {
			e.setCard(new AdhaarCard(randomAlphanumeric(12), "Pune", LocalDate.parse("2019-01-01")));
			dept.addEmployee(e);//setting up bi di asso
		});
		List<Employee> list2 = dao.saveAll(list);
		List<Address> adrList = List.of(
				new Address("Tilak Rd", "Pune", "MH", "India", "411020"),
				new Address("M G Rd", "Bangalore", "KA", "India", "566020"),
				new Address("Laxmi Rd", "Pune", "MH", "India", "411001"));
		assertEquals(3, list2.size());
		for(int i=0;i<list2.size();i++)
		{
			adrList.get(i).setOwner(list2.get(i));//setting up uni dir asso from Adr -->Emp
			adrDao.save(adrList.get(i));//saving adr details explicitly
		}	
	}

}
