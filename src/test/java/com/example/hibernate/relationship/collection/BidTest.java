package com.example.hibernate.relationship.collection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.example.hibernate.BaseTest;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class BidTest extends BaseTest {

	private static UUID itemId = null;
	
	//@Test
	public void aPersistTest() {
		Item item = new Item();
		item.setName("Car");
		em.persist(item);
		
		Bid bid = new Bid();
		bid.setBidValue(500000);
		bid.setItem(item);
		em.persist(bid);
	}
	
	//@Test
	public void bPersistWithCascadTest() {
		Item item = new Item();
		item.setName("Bike");
		
		Bid bid = new Bid();
		bid.setBidValue(500000);
		item.addBid(bid);
		
		Bid bid2 = new Bid();
		bid.setBidValue(600000);
		item.addBid(bid2);
		
		em.persist(item);
		
		assertNotNull(item.getItemId());
		assertTrue(em.contains(item));
		itemId = item.getItemId();
	}
	
	// we can use @OrderBy jpa annotation to fix default the collection retrieval order. 
	@Test
	public void baCollectionOrderTest() {
		Item item = em.find(Item.class, UUID.fromString("0b96c502-3421-47ad-9ac3-513c44a78d20"));
		Set<Bid> bids = item.getBids();
		Integer maxBidValue  = bids.stream().max(Comparator.comparing(Bid::getBidValue)).get().getBidValue();
		Integer tempBidValue = -700;
		
		for (Bid bid : bids) {
			if (bid.getBidValue() > tempBidValue) {
				tempBidValue = bid.getBidValue();
				continue;
			} else {
				System.out.println("bid ordering is incorrect");
				break;
			}
		}
		assertEquals(maxBidValue, tempBidValue);
	}
	
	
	/*while cascad remove operation, hibernate do not delete all the collection entities using single delete command rather it executes single delete command
	for each entity in collection
	Reason : While cascad remove operation, hibernate needs to call single delete statment for each delete operation and can not delete all entries in collection
	using single delete statment because, hibernate has to strictly follow entity life cycle while any cascade operation. SO
	it first selects the all entities using select statement and then follow delete cycle for each enity. It also needs to call beforeRemove() onRemove()
	callback listener registered on entity type*/
	@Test
	public void cDeleteItemWithCascadTest() {
		em.remove(em.getReference(Item.class, itemId));
	}
	
	/* Orphan remove allows us to delete etries from database when removed from managed collection. we can remove entity from collection
	 and hibernate will remove that entity from database. We can call clear() method on collection and hibernate will remove all entities from database.
	 BUT we should always remember that, the collection association is managed by hibernate. I.e all collections (i.e one-to-many) are managed by hibernate.
	 we can not assign new collection or null value to that managed collection i.e we can not do below thing, hibernate will throw exception 
	 item.setBids(null)  OR
	 item.setBids(new Hashset()
	
	We should always add/remove or change the elements in the colletions and we should never assign new collection or null value to it, Hibernate will not
	do orphan operation in that case. Reason is simple, as we already know, hibernate maitains proxy object for collection and manages that, if reassign colection
	to something different then hibernate won't be able to manage that collectiona and will give error while comminting th transaction if cascad/orphan remove 
	operation is enabled. 
	*/
	@Test
	public void dDeleteBidsOrphanTest() {
		Item item = em.find(Item.class, itemId);
		assertNotNull(item);
		assertTrue(em.contains(item));
		
		item.removeAllBids();
	}
}
