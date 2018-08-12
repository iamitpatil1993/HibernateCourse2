package com.example.hibernate.flushmode;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.UUID;

import javax.persistence.FlushModeType;

import org.junit.Test;

import com.example.hibernate.BaseTest;
import com.example.hibernate.demo.HelloWorld;
import com.example.hibernate.relationship.singlevalue.selfreferencing.Employee;

/**
 * 
 * Test to demonstrate FlushModeType in JPA at both query level and at entity manager level
 * 
 * Use Case:
 * refer 
 * 1. https://vladmihalcea.com/how-do-jpa-and-hibernate-define-the-auto-flush-mode/
 * 2. https://vladmihalcea.com/how-does-the-auto-flush-work-in-jpa-and-hibernate/
 * 3. https://vladmihalcea.com/a-beginners-guide-to-jpahibernate-flush-strategies/
 *
 */
public class FlushModeTest extends BaseTest {

	@Test
	public void testCommitFlushModeTypeAtEntityManger() {
		em.setFlushMode(FlushModeType.COMMIT);
		Employee emp = new Employee("Amit Patil");
		em.persist(emp);

		// flush mode COMMIT will cause not to insert before below select, so even though below
		// query result overlaps with persistence context pending changes, entity manager will not
		// executing insert(i.e flush current persistence context) before exeucing below query and 
		// will flush at transaction commit time only.
		em.createQuery("SELECT count(e.employeeId) FROM Employee e")
		.getSingleResult();
	}

	@Test
	public void testAutoFlushModeTypeAtEntityManger() {
		HelloWorld world = new HelloWorld();
		em.persist(world);

		Employee emp = new Employee("Amit Patil");
		em.persist(emp);

		// flush mode AUTO will cause to insert(persistence context flush) before below select
		// NOTE: Entity manager not only flushes the related entity pending changes(employee here)
		// rather flushes entire persistence context(so, HelloWorld entity insert will also 
		// occur before executing bellow JPQL query)
		em.createQuery("SELECT count(e.employeeId) FROM Employee e")
		.getSingleResult();
	}

	@Test
	public void testCommitFlushModeTypeAtQueryLevel() {
		Employee emp = new Employee("Amit Patil");
		em.persist(emp);

		// This will override FlushMode at em level for this query only.
		em.createQuery("SELECT count(e.employeeId) FROM Employee e")
		.setFlushMode(FlushModeType.COMMIT) // overriding FlushMode at entity manager level.
		.getSingleResult();

		Employee emp2 = new Employee("Ramesh Patil");
		em.persist(emp2);

		// Now FlushMode for below query is AUTO, so it will cause all above two insert to occur
		// before executing the query
		em.createQuery("SELECT count(e.employeeId) FROM Employee e")
		.getSingleResult();
	}
}

