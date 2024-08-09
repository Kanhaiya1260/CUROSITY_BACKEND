package com.app.dao;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Random;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.app.dao.EmployeeDao;
import com.app.entities.Employee;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
@Transactional//(propagation = Propagation.NOT_SUPPORTED)
class EmployeeRepositoryTestOptimistic {
	@Autowired
	private EmployeeDao empRepo;

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
		Employee e1 = empRepo.findById(1l).get();
		Employee e2 = empRepo.findById(1l).get();
		e1.setFirstName("abc");
		System.out.println(e1.getId());
		empRepo.save(e1);// JpaSystemException: Don't change the reference to a collection with
							// delete-orphan enabled :
		OptimisticLockingFailureException exception = assertThrows(OptimisticLockingFailureException.class, () -> {
			e2.setFirstName("def");
			empRepo.save(e2);
		});
		System.out.println("exc " + exception);
	}

	@Test
	void testOptimisticLockException2() {
		//simple unit test for optismistic locking , w/o uaing threads
		Employee e1 = empRepo.findById(1l).get();
		Employee e2 = empRepo.findById(1l).get();
		System.out.println("orig sal " + e1.getSalary());
		e1.setSalary(e1.getSalary() + 1000);
		System.out.println(e1.getId());
		empRepo.save(e1);// JpaSystemException: Don't change the reference to a collection with
							// delete-orphan enabled :
		// OptimisticLockingFailureException exception =
		// assertThrows(OptimisticLockingFailureException.class, () -> {
		e2.setSalary(e2.getSalary() + 3000);
		System.out.println(e2.getId());
		empRepo.save(e2);
		// });
		// System.out.println("exc "+exception);
	}

	@Test
	void testOptimisticLockException3() throws Exception{
		Random r1=new Random();
		Thread t1 = new Thread(() -> {
			System.out.println(Thread.currentThread()+" strted");
			Employee e1 = empRepo.findById(1l).get();
			System.out.println("orig sal " + e1.getSalary());
			try {
				Thread.sleep(10+r1.nextInt(200));
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
			e1.setSalary(e1.getSalary() + 1000);
			empRepo.save(e1);
			System.out.println(Thread.currentThread()+" over");
		});
		Thread t2 = new Thread(() -> {
			System.out.println(Thread.currentThread()+" strted");
			Employee e2 = empRepo.findById(1l).get();
			System.out.println("orig sal " + e2.getSalary());
			try {
				Thread.sleep(10+r1.nextInt(50));
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
			e2.setSalary(e2.getSalary() + 2000);
			empRepo.save(e2);
			System.out.println(Thread.currentThread()+" over");
		});
		t1.start();
		t1.join();
		t2.start();		
		t2.join();
		System.out.println("test over");

		
	}
	/*
	 * Better demo exists in : D:\ACTSMar2023\code\spring-boot-optimistic-locking
	 * Test class : ProductPurchaseServiceTest
	 */
	@Test
	void testOptimisticLockException4() throws Exception{
		Random r1=new Random();
		Thread t1 = new Thread(() -> {
			System.out.println(Thread.currentThread()+" strted");
			Employee e1 = empRepo.findById(1l).get();
			System.out.println("orig sal " + e1.getSalary());
			try {
				Thread.sleep(10+r1.nextInt(40));
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
			e1.setSalary(e1.getSalary() + 1000);
			empRepo.save(e1);
			System.out.println(Thread.currentThread()+" over");
		});
		Thread t2 = new Thread(() -> {
			System.out.println(Thread.currentThread()+" strted");
			Employee e2 = empRepo.findById(1l).get();
			System.out.println("orig sal " + e2.getSalary());
			try {
				Thread.sleep(10+r1.nextInt(500));
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
			e2.setSalary(e2.getSalary() + 2000);
			empRepo.save(e2);
			System.out.println(Thread.currentThread()+" over");
		});
		t1.start();
		t2.start();		
		t1.join();		
		t2.join();
		System.out.println("test over");

		
	}
}
