package com.example.hibernate.demo;

import static org.junit.Assert.*;

import org.junit.Test;

import com.example.hibernate.BaseTest;

public class HelloWorldTest extends BaseTest {

	@Test
	public void test() {
		HelloWorld helloWorld = new HelloWorld();
		em.persist(helloWorld);
		assertNotNull(helloWorld.getId());
	}
}
