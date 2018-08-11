package com.example.hibernate.readonlypersistencecontext;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.hibernate.Session;
import org.junit.Test;

import com.example.hibernate.BaseTest;
import com.example.hibernate.relationship.singlevalue.selfreferencing.Employee;

/*
 * Test to demonstrate Read-Only persistence context.
 * 
 * Use Case :
 * Sometimes we want to read lot many entities via entity manager apis or Query interface, or we have long running transaction. In reports modules, we need to read lots of 
 * entities many be hundreds or thousands. There are two optimizations we can do.
 * 1. Do not use transaction i.e do not associate em with transaction and use persistence context only.
 * 2. Make the entity manager, specific entity or query results read-only
 * 
 * When we load entity, hibernate creates snapshot of each managed entity and dirty checks the state of entity. An do operations on database accordingly. But,
 * in report type queries where we do notwant to do any write operations, why to create such a snapshots of thousands of entities to waste memory.
 * We can avoid durty-checking and snapshot creation and still have entities managed in persistence context. But when transaction ends nothing will get writtent to database.
 * 
 * Solution:
 * Hibernate supports
 * 1. Read-only persistent context/ session
 * 2. Read-only specific entity.
 * 3. Make query interface results reaad only(I.e do not make snapshots of entities fetched via Query interface)
 * 
 * How to use:
 * 
 * To use it we need to uwrap the entity manager to get underlying hibernate session object and call setDetaultReadOnly(true) to make current persistence context read-only
 * To make specific entity read-only, unwrap the em to uderlying hibernate session object and call setReadOnly(entityObject, true).
 * To make Query result read-only, we need to set query hint before exeuting query (hint = org.hibernate.annotations.QueryHints.READ_ONLY)
 *
 */
public class ReadOnlyPersistenceContextTest extends BaseTest {

	@Test
	public void testReadOnlyPersistenceContext() {
		EntityManagerFactory entityManagerFactory;
		EntityManager em;
		EntityTransaction entityTransaction;
		entityManagerFactory = Persistence.createEntityManagerFactory("JPADB");
		em = entityManagerFactory.createEntityManager();
		entityTransaction = em.getTransaction();

		// Create/Remove Entity is allowed when setDefaultReadOnly is set to true. Only
		// update operation not allowed
		entityTransaction.begin();
		em.unwrap(Session.class).setDefaultReadOnly(true);
		Employee helloWorld = new Employee("AMit Patil");
		em.persist(helloWorld);
		entityTransaction.commit();

		// Try to change the state in another transaction. But will not update state in
		// database only updates in persistence context
		entityTransaction.begin();
		em.flush();
		em.clear();
		helloWorld = em.find(Employee.class, helloWorld.getEmployeeId());
		assertNotNull(helloWorld);
		helloWorld.setFullName("Ramesh");
		em.merge(helloWorld);
		entityTransaction.commit();

		// Fetch state from database and ssert.
		entityTransaction.begin();
		em.flush();
		em.clear();
		helloWorld = em.find(Employee.class, helloWorld.getEmployeeId());
		assertNotEquals("Ramesh", helloWorld.getFullName());
		assertEquals("AMit Patil", helloWorld.getFullName());
		entityTransaction.commit();
		em.close();
	}

	@Test
	public void testEntityReadOnly() {

		EntityManagerFactory entityManagerFactory;
		EntityManager em;
		EntityTransaction entityTransaction;
		entityManagerFactory = Persistence.createEntityManagerFactory("JPADB");
		em = entityManagerFactory.createEntityManager();
		entityTransaction = em.getTransaction();

		// Create/Remove Entity is allowed when setDefaultReadOnly is set to true. Only
		// update operation not allowed
		entityTransaction.begin();
		Employee helloWorld = new Employee("AMit Patil");
		em.persist(helloWorld);
		entityTransaction.commit();

		// Try to change the state in another transaction. But will not update state in
		// database only updates in persistence context
		entityTransaction.begin();
		em.flush();
		em.clear();
		helloWorld = em.find(Employee.class, helloWorld.getEmployeeId());
		assertNotNull(helloWorld);
		em.unwrap(Session.class).setReadOnly(helloWorld, true); // this sets only this particular entity to be read
																// only.
		helloWorld.setFullName("Ramesh");
		em.merge(helloWorld);
		entityTransaction.commit();

		// Fetch state from database and ssert.
		entityTransaction.begin();
		em.flush();
		em.clear();
		helloWorld = em.find(Employee.class, helloWorld.getEmployeeId());
		assertNotEquals("Ramesh", helloWorld.getFullName());
		assertEquals("AMit Patil", helloWorld.getFullName());
		entityTransaction.commit();
		em.close();
	}

	@Test
	public void testQueryReadOnly() {

		EntityManagerFactory entityManagerFactory;
		EntityManager em;
		EntityTransaction entityTransaction;
		entityManagerFactory = Persistence.createEntityManagerFactory("JPADB");
		em = entityManagerFactory.createEntityManager();
		entityTransaction = em.getTransaction();

		// Create/Remove Entity is allowed when setDefaultReadOnly is set to true. Only
		// update operation not allowed
		entityTransaction.begin();
		Employee helloWorld = new Employee("AMit Patil");
		em.persist(helloWorld);
		entityTransaction.commit();

		// Try to change the state in another transaction. But will not update state in
		// database only updates in persistence context
		entityTransaction.begin();
		em.flush();
		em.clear();
		// fetch entity using entity interface in read only mode. Entity fetched by the
		// query are managed but read-only
		helloWorld = em.createQuery("SELECT e FROM Employee e WHERE e.employeeId = :employeeId", Employee.class)
				.setParameter("employeeId", helloWorld.getEmployeeId())
				.setHint(org.hibernate.annotations.QueryHints.READ_ONLY, true).getSingleResult();
		assertNotNull(helloWorld);
		helloWorld.setFullName("Ramesh");
		em.merge(helloWorld);
		entityTransaction.commit();

		// Fetch state from database and ssert.
		entityTransaction.begin();
		em.flush();
		em.clear();
		helloWorld = em.find(Employee.class, helloWorld.getEmployeeId());
		assertNotEquals("Ramesh", helloWorld.getFullName());
		assertEquals("AMit Patil", helloWorld.getFullName());
		entityTransaction.commit();
		em.close();
	}
}
