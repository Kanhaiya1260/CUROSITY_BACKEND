package com.app.dao;

import static org.junit.jupiter.api.Assertions.*;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.app.dao.DepartmentDao;
import com.app.entities.Department;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Transactional(propagation = Propagation.NOT_SUPPORTED)
class DepartmentRepositoryTestOptimistic {
	@Autowired
	private DepartmentDao deptRepo;

	/*
	 * // Store the saved entity in two separate entity objects ProductEntity
	 * entity1 = repository.findById(savedEntity.getId()).get(); ProductEntity
	 * entity2 = repository.findById(savedEntity.getId()).get();
	 * 
	 * // Update the entity using the first entity object entity1.setName("n1");
	 * repository.save(entity1);
	 * 
	 * // Update the entity using the second entity object. // This should fail
	 * since the second entity now holds an old version number, // i.e. an
	 * Optimistic Lock Error assertThrows(OptimisticLockingFailureException.class,
	 * () -> { entity2.setName("n2"); repository.save(entity2); });
	 * 
	 * // Get the updated entity from the database and verify its new sate
	 * ProductEntity updatedEntity = repository.findById(savedEntity.getId()).get();
	 * assertEquals(1, (int) updatedEntity.getVersion()); assertEquals("n1",
	 * updatedEntity.getName());
	 */
	@Test
	void testOptimisticLockException() {
		Department dept1 = deptRepo.findById(1l).get();
		Department dept2 = deptRepo.findById(1l).get();
		dept1.setDeptName("d1");
		deptRepo.save(dept1);//JpaSystemException: Don't change the reference to a collection with delete-orphan enabled : 
		OptimisticLockingFailureException exception = assertThrows(OptimisticLockingFailureException.class, () -> {
			dept2.setDeptName("d2");
			deptRepo.save(dept2);
		});
		System.out.println("exc "+exception);
	}

}
