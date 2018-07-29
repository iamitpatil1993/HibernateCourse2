package com.example.hibernate.unwrap;

import static org.junit.Assert.assertNotNull;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.stat.Statistics;
import org.junit.Test;

import com.example.hibernate.BaseTest;
import com.example.hibernate.demo.HelloWorld;

/**
 * 
 * @author amit
 * This test it to demonstrate the use of EntityManager#unwrap() and EntityManagerFactory#unwrap() to use get provider specific counterpart of JPA implementation
 * and use them to perform provider specific opearations.	
 */
public class EntityManagerUnwrapTest extends BaseTest {

	@Test
	public void test() {
		// Get native hibernate session object using EntityManager#unwrap() and use it to perform provider specific operations
		Session session = em.unwrap(Session.class);
		HelloWorld helloWorld = new HelloWorld();
		Long id = (Long) session.save(helloWorld);
		assertNotNull(id);

		// Get native hibernate sessionFactory object using EntityManagerFactory#unwrap() and use it to perform provider specific operations
		SessionFactory sessionFactory = em.getEntityManagerFactory().unwrap(SessionFactory.class);
		assertNotNull(sessionFactory);
		Statistics statistics = sessionFactory.getStatistics();
		assertNotNull(statistics);
		System.out.println("sessionFactory Statistics are :: " + statistics);
	}
}
