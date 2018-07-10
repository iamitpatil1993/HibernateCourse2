package com.example.hibernate.relationship.onetomany;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.UUID;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.example.hibernate.BaseTest;

/**
 * 
 * @author amit 
 * This is demo to demonstrate bags with one-to-many relationship.
 * This demo shows that, if we map the collection as bags (Collection) and not List/set then,
 * it is possible to add new elements to bags withould loading the collection. In contrast, if 
 * collection type is List/Set then as soon as we access the collection hibernate will load entire
 * collection.
 * This is possible for bags because, bags allows duplicate elements and also do not maitain any ordering/index
 * so while adding new element to bags collection hibernate do not need to check uniqueness as well as 
 * no need to manage ordering/index.
 */

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PersonTest extends BaseTest {

	private static UUID personId;
	@Test
	public void aCreatePersonWithInterests() {
		Person person = new Person("Amit Patil");
		
		Interest interest = new Interest("Music");
		person.addInterest(interest);
		
		Interest interest2 = new Interest("Travel");
		person.addInterest(interest2);
		
		em.persist(person);
		assertNotNull(person.getPersonId());
		assertTrue(em.contains(person));
		
		personId = person.getPersonId();
	}
	
	@Test
	public void bGetPersonTest() {
		Person per = em.find(Person.class, personId);
		System.out.println("person interests :: " + per.getInterests().size());
	}
	
	@Test
	public void cAddIntererstToPersonTest() {
		Person per = em.find(Person.class, personId);
		
		// this operation will not load all interets of person. We can add new elements to bag
		// without loading the collection.
		Interest interest2 = new Interest("Dance");
		per.addInterest(interest2);
		
		em.merge(per);
	}

}
