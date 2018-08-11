package com.example.hibernate.dynamicsql;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.UUID;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.example.hibernate.BaseTest;

/*
 * This test is to demonstrate dynamic sql generation in hibernate.
 * Use case:
 * Hibernate needs to execute lots of insert and update statements on each entity, and query for each query is different for each update/insert statement.
 * For example, we have update statement on entity which updates 3/5 columns, also we have other update statement which updates 1/5 coumns of same entity type.
 * So, issue is hibernate has to create sql for each such unique query at runtime, which can be time consuming.
 * So, to avoid this runtime overhead, hibernate by default creates update and insert statments for each entity type at start up and cache them.
 * The queries created includes all the columns in table, so at runtime if we are updating/inserting very few columns it replaces values in query for
 * those columns and for all other columns which we are not inserting/updating it assign old value for update and null value for insert.
 * This works greate as long as table has less columns, but if we have table with hundreds of coulmns then including all columns in each insert and update
 * may be costly and unnecessary. In such a cases we should only columns we are updating in update statements.
 * 
 *  Solution:
 *  Hibernate (Not JPA) privides two annotations
 *  org.hibernate.persistence.dynamicInsert : This disables the query generation at start up and generates the insert query at runtime(every time) with actual
 *  											columns getting inserted.
 *  org.hibernate.persistence.dynamicUpdate :  This disables the query generation at start up and generates the update query at runtime(every time) with actual
 *  											columns getting updated.
 *  
 *  How to use:
 *  Just add
 *  @org.hibernate.persistence.dynamicInsert
 *  @org.hibernate.persistence.dynamicUpdate 
 *  
 *  at entity class level.
 */

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class DynamicSqlDemoEntityTest extends BaseTest {

	private static UUID id = null;

	/*
	 * Since DynamicSqlDemoEntity is annoted with org.hibernate.persistence.dynamicInsert this insert will get generated every time at runtime.
	 */
	@Test
	public void testAInsert() {
		DynamicSqlDemoEntity demoEntity = new DynamicSqlDemoEntity();
		demoEntity.setCol1("dummyValue" + UUID.randomUUID().toString());
		em.persist(demoEntity);
		assertNotNull(demoEntity.getId());
		assertTrue(em.contains(demoEntity));
		id = demoEntity.getId();
	}
	
	/*
	 * Since DynamicSqlDemoEntity is annoted with org.hibernate.persistence.dynamicUpdate this update will get generated every time at runtime with
	 * only columns that are getting updated.
	 */
	@Test
	public void testBUpdate() {
		DynamicSqlDemoEntity demoEntity = em.find(DynamicSqlDemoEntity.class, id);
		demoEntity.setCol1("dummyValue" + UUID.randomUUID().toString());
	}
}
