package com.example.hibernate.em;

import static org.junit.Assert.*;

import org.junit.Test;

import com.example.hibernate.BaseTest;
import com.example.hibernate.relationship.singlevalue.User;

public class EntityManagerFlushTest extends BaseTest {

	// EntityManager#flush() only synchronizes  the currently managed entites to database by
	// applying dirty check on them.
	// It does not detaches the entities after synchronization, all entities remains managed.
	@Test
	public void flushTest() {
		User user = new User("Amit Patil");
		em.persist(user);
		assertTrue(em.contains(user));
		
		// flush the persistence context
		em.flush();
		
		// check whether it only synchronizes the the state or detaches all entities as well?
		assertTrue(em.contains(user));
	}
	
	@Test
	public void clearTest() {
		User user = new User("Amit Patil");
		em.persist(user);
		assertTrue(em.contains(user));
		
		// flush the persistence context
		em.flush();
		
		// check whether it only synchronizes the the state or detaches all entities as well?
		assertTrue(em.contains(user));
		
		// clears the persistence context
		// but our entity will get stored to database because, we have already flushed the persistence context before.
		em.clear(); 
		
		// this will return false now.
		assertFalse(em.contains(user));
	}
}
