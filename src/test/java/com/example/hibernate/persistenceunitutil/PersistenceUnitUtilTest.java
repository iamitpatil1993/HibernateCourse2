package com.example.hibernate.persistenceunitutil;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.UUID;

import javax.persistence.PersistenceUnitUtil;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.example.hibernate.BaseTest;
import com.example.hibernate.relationship.singlevalue.User;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PersistenceUnitUtilTest extends BaseTest {

	private static UUID userId = null;

	@Test
	public void aCreateUserWithoutAddressTest() {
		User user = new User("amipatil");
		em.persist(user);
		assertNotNull(user.getUserId());
		assertTrue(em.contains(user));
		userId = user.getUserId();
	}

	@Test
	public void bGetIdentifierWithPersistentEntityTest() {
		User user = em.find(User.class, userId);
		PersistenceUnitUtil persistenceUnitUtil = em.getEntityManagerFactory().getPersistenceUnitUtil();
		assertEquals(userId, persistenceUnitUtil.getIdentifier(user));
	}
	
	
	// If entity instance is transient PersistenceUnitUtil#getIdentifier() do not gives exception, it simply returns null.
	@Test
	public void bGetIdentifierWithTransientEntityTest() {
		User user = new User("amipatil");
		PersistenceUnitUtil persistenceUnitUtil = em.getEntityManagerFactory().getPersistenceUnitUtil();
		assertEquals(null, persistenceUnitUtil.getIdentifier(user));
	}
	
	@Test
	public void bIsLoadedWithPersistentEntityTest() {
		User user = em.find(User.class, userId);
		PersistenceUnitUtil persistenceUnitUtil = em.getEntityManagerFactory().getPersistenceUnitUtil();
		assertTrue(persistenceUnitUtil.isLoaded(user));
	}
	
	
	@Test
	public void bIsLoadedWithTransientEntityTest() {
		User user = new User("amipatil");
		PersistenceUnitUtil persistenceUnitUtil = em.getEntityManagerFactory().getPersistenceUnitUtil();
		System.out.println("isLoaded :: " + persistenceUnitUtil.isLoaded(user));
		assertFalse(persistenceUnitUtil.isLoaded(user));
	}
}
