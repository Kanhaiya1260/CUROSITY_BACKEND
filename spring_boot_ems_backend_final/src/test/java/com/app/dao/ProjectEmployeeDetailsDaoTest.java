package com.app.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import com.app.entities.Employee;
import com.app.entities.Project;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
class ProjectEmployeeDetailsDaoTest {
	@Autowired
	private ProjectEmployeeDetailsDao dao;

	@Test
	void testFindByProjectId() {
		List<Employee> list = dao.findByProjectId(1l);
		list.forEach(System.out::println);
		assertEquals(2,list.size());
	}
	
	@Test
	void testFindByEmpId() {
		List<Project> list = dao.findByEmpId(2l);
		list.forEach(System.out::println);
		assertEquals(2,list.size());
	}

}
