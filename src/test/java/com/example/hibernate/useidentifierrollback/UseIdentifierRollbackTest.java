package com.example.hibernate.useidentifierrollback;

import static org.junit.Assert.assertNull;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.junit.Test;

import com.example.hibernate.BaseTest;
import com.example.hibernate.demo.HelloWorld;
import com.example.hibernate.relationship.singlevalue.selfreferencing.Employee;

/*
 * This test is to demonstrate hibernate.use_identifier_rollback configuration property in hibernate.
 * Use Case:
 * When we delete the entity, we often expect it to be transient again. WHich means it will become detached and non-managed and will also get delteed
 * from database once transaction gets commited.
 * But pure transient entities has their identifier value to null and also we ofent use PersistenceUnitUtil.getIdentifier() to check entity is
 * transient. But deleting the entity using entity manager does not assigns null value to identifier. So, how to do this?
 * 
 * Solution:
 * Hibernate has one configuration property hibernate.use_identifier_rollback which takes true/false value. When this is enabled in persistence.xml
 * hibernate will assign null value after entity deletion to entity identifer.
 * 
 * NOTE: This works only for generated/assigned identifier. If identifier is assigned by application code then this may not work.
 * 
 * How to use:
 * add
 *  <property name="hibernate.use_identifier_rollback" value = "true"/>
 *  
 *  in persistence.xml. and you can see the results right away.
 */

public class UseIdentifierRollbackTest extends BaseTest {

	/*
	 * This is test for database generated Identifier. It works in this case.
	 */
	@Test
	public void testForDatabaseGeneratedIdentifier() {
		EntityManagerFactory entityManagerFactory;
		EntityManager em;
		EntityTransaction entityTransaction;
		entityManagerFactory = Persistence.createEntityManagerFactory("JPADB");
		em = entityManagerFactory.createEntityManager();
		entityTransaction = em.getTransaction();
		entityTransaction.begin();
		
		HelloWorld helloWorld = new HelloWorld();
		em.persist(helloWorld);
		
		System.out.println("Entity Identifier after creation :: " + helloWorld.getId());
		entityTransaction.commit();
		
		entityTransaction.begin();
		em.remove(helloWorld);
		entityTransaction.commit();
		em.clear();
		
		assertNull(helloWorld.getId());
		System.out.println("Entity Identifier after removal(This will be null) :: " + helloWorld.getId());
	}
	

	@Test
	public void testForUUIDIdentifierGeneratedAtApplication() {
		EntityManagerFactory entityManagerFactory;
		EntityManager em;
		EntityTransaction entityTransaction;
		entityManagerFactory = Persistence.createEntityManagerFactory("JPADB");
		em = entityManagerFactory.createEntityManager();
		entityTransaction = em.getTransaction();
		entityTransaction.begin();
		
		Employee helloWorld = new Employee();
		em.persist(helloWorld);
		
		System.out.println("Entity Identifier after creation :: " + helloWorld.getEmployeeId());
		entityTransaction.commit();
		
		entityTransaction.begin();
		em.remove(helloWorld);
		entityTransaction.commit();
		em.clear();
		
		assertNull(helloWorld.getEmployeeId());
		System.out.println("Entity Identifier after removal(This will be null) :: " + helloWorld.getEmployeeId());
	}
}
