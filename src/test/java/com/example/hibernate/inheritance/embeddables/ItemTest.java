package com.example.hibernate.inheritance.embeddables;

import static org.junit.Assert.assertNotNull;

import java.util.UUID;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.example.hibernate.BaseTest;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ItemTest extends BaseTest {

	private static UUID id = null;
	
	@Test
	public void testAPersist() {
		Height height = new Height("CM", "'", 168l);
		Weight weight = new Weight("Kg", "kg", 68l);
		
		Item item = new Item();
		item.setHeight(height);
		item.setWeight(weight);
		
		em.persist(item);
		
		assertNotNull(item.getItemId());
		id = item.getItemId();
	}
	
	@Test
	public void testBFind() {
		Item item = em.find(Item.class, id);
		assertNotNull(item);
		assertNotNull(item.getWeight());
		assertNotNull(item.getHeight());
	}

}
