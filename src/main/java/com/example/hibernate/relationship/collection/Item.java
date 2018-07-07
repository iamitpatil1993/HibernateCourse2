package com.example.hibernate.relationship.collection;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.example.hibernate.BaseEntity;

@Entity
@Table(name = "relatonshp_item")
public class Item extends BaseEntity {

	private static final long serialVersionUID = -8717763628947889167L;

	@Id
	@Basic
	@GeneratedValue(generator = "item_id_generator")
	@GenericGenerator(name = "item_id_generator", strategy = "uuid2")
	@Column(name = "item_id")
	private UUID itemId;

	@Basic
	@Column(name = "name")
	private String name;

	@OneToMany(mappedBy = "item", cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval = true)
	@OrderBy("bidValue desc")
	private Set<Bid> bids = new HashSet<>();

	public void setBids(Set<Bid> bids) {
		this.bids = bids;
	}

	public Set<Bid> getBids() {
		return bids;
	}

	public UUID getItemId() {
		return itemId;
	}

	public void setItemId(UUID itemId) {
		this.itemId = itemId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void addBid(Bid bid) {
		if (bid != null) {
			bid.setItem(this);
			this.bids.add(bid);
		}
	}
	
	public void removeBid(Bid bid) {
		if (bid != null) {
			this.bids.remove(bid);
		}
	}
	
	public void removeAllBids() {
		this.bids.clear();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((itemId == null) ? 0 : itemId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Item)) {
			return false;
		}
		Item other = (Item) obj;
		if (itemId == null) {
			return false;
		}	
		return itemId.equals(other.getItemId());
	}
}
