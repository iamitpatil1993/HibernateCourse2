package com.example.hibernate.em;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.example.hibernate.BaseTest;
import com.example.hibernate.relationship.singlevalue.User;

/**
 * 
 * @author amit
 * This test case is to check {@link EntityManager#getReference(Class, Object)} behaviour in different scenarios
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class EntityManagerGetReferenceTest extends BaseTest {

	private static UUID userId = null;

	@Test
	public void aInit() {
		User user = new User("amipatil");
		em.persist(user);
		assertNotNull(user.getUserId());
		assertTrue(em.contains(user));
		userId = user.getUserId();
	}
	
	/*
	 * EntityManager#contains() return true for proxy entity reference.
	 */
	@Test
	public void entityManagerContainsWithValidPKTest() {
		User user = em.getReference(User.class, userId);
		assertTrue(em.contains(user));
	}

	/*
	 * EntityManager#contains() return true for proxy entity reference irrepective
	 * of Entity exists by primary key or not. Because it is pure proxy objecy and
	 * get initialized when we access the entity state using getters(excluding
	 * primary key)
	 */
	@Test
	public void entityManagerContainsWithInValidPKTest() {
		User user = em.getReference(User.class, UUID.randomUUID());
		assertTrue(em.contains(user));
	}

	// Proxy state get initialized only when we access state other than primary key
	@Test
	public void loadedTestWithPrimaryKeyFiedAccessTest() {
		User user = em.getReference(User.class, userId);
		assertTrue(em.contains(user));

		// we are accessing state (PK) on proxy, but pk does not trigger entity
		// initialization since it is
		// part of proxy object from it's instantiation
		user.getUserId();

		boolean isLoaded = em.getEntityManagerFactory().getPersistenceUnitUtil().isLoaded(user);
		assertFalse(isLoaded); /// isLoaded == false
	}

	// Proxy state get initialized only when we access state other than primary key
	@Test
	public void loadedTestWithNonPrimaryKeyFiedAccessTest() {
		User user = em.getReference(User.class, userId);
		assertTrue(em.contains(user));

		// we are accessing state (PK) on proxy, but pk does not trigger entity
		// initialization since it is
		// part of proxy object from it's instantiation
		user.getUsername();

		boolean isLoaded = em.getEntityManagerFactory().getPersistenceUnitUtil().isLoaded(user);
		assertTrue(isLoaded); // isLoaded == true
	}

	// We get entityNotFoundException at the point when entity manager starts
	// loading the proxy state.
	// When we will access the state(other than pk) we will get this exception.
	@Test(expected = EntityNotFoundException.class)
	public void loadedTestWithWrongPrimaryKeyTest() {
		User user = em.getReference(User.class, UUID.randomUUID()); // never returns null, always returns proxy.
		assertTrue(em.contains(user));

		user.getUsername(); // This will throw EntityNotFoundException
	}

}
