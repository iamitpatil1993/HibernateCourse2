package com.example.hibernate.refresh;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.example.hibernate.BaseTest;
import com.example.hibernate.relationship.singlevalue.User;

/**
 * 
 * @author amit
 * Test case to demonstrate: Try executing refresh() on managed entity and check what sql it fires? Does is completely refresh entity? Is sql executed on first 
	entity fetch and refresh() same?
	
	Conclustion : Yes sql executed at entity find and refresh are exactly same.
 */
public class EntityManagerRefreshTest extends BaseTest {

	@Test
	public void refreshTest() {
		// Create user(entity) first
		User user = new User("amipatil");
		em.persist(user);
		assertNotNull(user.getUserId());
		assertTrue(em.contains(user));

		// flush the current persistence context and clear it to detach the managed entity instance
		em.flush();
		em.clear();
		
		// fetch the entity by pk
		user = em.find(User.class, user.getUserId());
		
		// refresh it
		em.refresh(user);
		
		//assert sql at entity fetch and refresh is same
	}
}
