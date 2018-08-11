package com.example.hibernate.immutable;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.UUID;

import javax.persistence.PersistenceException;

import org.hibernate.HibernateException;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.example.hibernate.BaseTest;

/*
 * This test is to demonstrate immutable entity in hibernate
 * 
 * Use case:
 * Some times we want to add constraint on entity that, entity can only be created and can not be updated once created.
 * To do so, we need to make entity instance Immutable irrespective of Entity manager.
 * 
 * Solution
 * We can use hibrenate's @Immutable annotation to mark entity to be immutable which will disable entity update operations.
 * Mark an Entity, a Collection, or an Attribute type as immutable. No annotation means the element is mutable.
An immutable entity may not be updated by the application. Updates to an immutable entity will be ignored, but no exception is thrown. @Immutable must be used on root entities only.
@Immutable placed on a collection makes the collection immutable, meaning additions and deletions to and from the collection are not allowed. A HibernateException is thrown in this case.
An immutable attribute type will not be copied in the currently running Persistence Context in order to detect if the underlying value is dirty. As a result loading the entity will require less memory and checking changes will be much faster.

 Prior to Hibernate 5.2.17, JPQL queries were not taking into consideration the @Immutable status of a given entity.

In Hibernate 5.2.17, a WARNING message is logged when we try to modify the Event entity via a JPQL bulk update statement. 


refer https://vladmihalcea.com/immutable-entity-jpa-hibernate/ 

 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ImmutableEntityDemoTest extends BaseTest {

	private static UUID id = null;
	
	@Test
	public void testaPersist() {
		ImmutableEntityDemo demo = new ImmutableEntityDemo("Dummy state");
		em.persist(demo);
		assertTrue(em.contains(demo));
		assertNotNull(demo.getId());
		id = demo.getId();
	}
	
	@Test
	public void testbUpdate() {
		ImmutableEntityDemo demo = em.find(ImmutableEntityDemo.class, id);
		assertTrue(em.contains(demo));
		assertNotNull(demo.getId());
		demo.setState("new State");
		em.merge(demo);
	}

	@Test
	public void testcAssertUpdate() {
		ImmutableEntityDemo demo = em.find(ImmutableEntityDemo.class, id);
		assertTrue(em.contains(demo));
		assertNotNull(demo.getId());
		assertNotEquals("new State", demo.getState());
		assertEquals("Dummy state", demo.getState());
	}
	
	@Test(expected = PersistenceException.class)
	public void testdUpdateUsiingQuery() {
		int rowsUpdated = em.createQuery("UPDATE ImmutableEntityDemo set state = :state WHERE id = :id").setParameter("state", "new state using query")
		.setParameter("id", id).executeUpdate();
		assertEquals(0, rowsUpdated);
	}
	
	@Test
	public void testeAssertUpdate() {
		ImmutableEntityDemo demo = em.find(ImmutableEntityDemo.class, id);
		assertTrue(em.contains(demo));
		assertNotNull(demo.getId());
		assertNotEquals("new state using query]", demo.getState());
		assertEquals("Dummy state", demo.getState());
	}
}
