package com.example.hibernate.relationship.collection;

import java.util.UUID;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.example.hibernate.BaseEntity;

@Entity
@Table(name = "bid")
public class Bid extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5360516478078684529L;

	@Id
	@Basic
	@GeneratedValue(generator = "item_id_generator")
	@GenericGenerator(name = "item_id_generator", strategy = "uuid2")
	@Column(name = "bid_id")
	private UUID bidId;

	@Basic
	@Column(name = "bid_value")
	private Integer bidValue;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "item_id")
	private Item item;

	public UUID getBidId() {
		return bidId;
	}

	public void setBidId(UUID bidId) {
		this.bidId = bidId;
	}

	public Integer getBidValue() {
		return bidValue;
	}

	public void setBidValue(Integer bidValue) {
		this.bidValue = bidValue;
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((bidId == null) ? 0 : bidId.hashCode());
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
		if (!(obj instanceof Bid)) {
			return false;
		}
		Bid other = (Bid) obj;
		if (bidId == null) {
			return false;
		}
	return bidId.equals(other.getBidId());
	}
}
