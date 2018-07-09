package com.example.hibernate.relationship.singlevalue;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.UUID;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.example.hibernate.BaseTest;
import com.example.hibernate.relationship.singlevalue.User;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserTest extends BaseTest {

	private static UUID userId = null;
	
	//@Test
	public void aCreateUserWithoutAddressTest() {
		User user = new User("amipatil");
		em.persist(user);
		assertNotNull(user.getUserId());
		assertTrue(em.contains(user));
		userId = user.getUserId();
	}
	
	@Test
	public void abCreateUserWithAddressTest() {
		User user = new User("iamitpatil1993");
		Address address = new Address();
		address.setCity("Pune");
		address.setStreet("Gajanan Hos. Soc.");
		
		user.setAddress(address);
		em.persist(user);
		assertNotNull(user.getUserId());
		assertTrue(em.contains(user));
		assertTrue(em.contains(address));
	}
	
	@Test
	public void bCreateAddressTest() {
		Address address = new Address();
		address.setCity("Pune");
		address.setStreet("Gajanan Hos. Soc.");
		address.setUser(em.getReference(User.class, userId));
		em.persist(address);
		assertNotNull(address.getAddressId());
		assertTrue(em.contains(address));
	}
	
	//@Test 
	public void cFindUser() {
		User user = em.find(User.class, userId);
		assertNotNull(user);
		assertTrue(em.contains(user.getUserId()));
	}
}
