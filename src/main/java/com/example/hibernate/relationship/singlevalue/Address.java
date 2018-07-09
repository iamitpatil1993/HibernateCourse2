package com.example.hibernate.relationship.singlevalue;

import java.util.UUID;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.example.hibernate.BaseEntity;;

/**
 * 
 * @author amit
 * This is single value association demo with User entity.
 *  Here, we have created single value association by adding join column (physical foreign key column) in child table.
 *  Parent table do not have foreign key rather child table have foreign key to parent.
 *  This model has drawbacks as 
 *  1. This chile table can not be reused for other entities, if this table is generic table like Address.
 *  2. There is possibility of multiple values in child table for parent entity, so we would need to add database constraint on child table.
 *  3. Adding constraint on child table will restrict us from reusing generic table for other entities.
 *  4. Since there is posibility of multiple entities for one particulat parenet entity, it will brack the one-to-one mapping
 *  	constraint and code will break at runtime and not compile time.
 *  5. This table one-to-one mapping can not be used with soft delete model, because of two entities have one-to-one relationshipe
 *  	and we add/remove child entity for parent then it will be soft delete, so jpa will give error while loading 
 *  	parenet entity if it finds multiple entities in child table for parent entity, it does not considers this soft delete
 *  	mechanism and only considers occurrences of parent pk key.
 *  6. In case of bidirectional one-to-one relationship, when we fetch parent entity, hibernate executes one additional query
 *  	to fetch this chile entity. Even if we map association to be lazy in parent entity, it will execute query to fetch child.
 *  	This is because, at the time of loading parent entity, hibernate do not know that wheter there child exits or not.
 *  	So, hibernate needs to execute another query to cheeck chile exits and get if exits. So conclusion is, lazy loading 
 *  	will not work even though we mark it to be lazy and mark it to be non optional relationship. For more information on this 
 *  	search on google.
 *  7. Since foreign key column is in child, we need to define one-to-one mapping to parent in child table. And since we seldom
 *  	select child entity, and most often we select parent entity, we need child entity while fetching parent, so we must define
 *  	one-to-one mapping in parent entity because relationship will be used most often from parent to chile and not child to parent.
 *    
 */ 

@Entity
@Table(name = "one_to_one_address")
public class Address extends BaseEntity {

	private static final long serialVersionUID = -7283739230740402800L;

	@Basic
	@Id
	@GeneratedValue(generator = "address_id_gen")
	@GenericGenerator(name = "address_id_gen", strategy = "uuid2")
	private UUID addressId;

	@Basic
	@Column(name = "city")
	private String city;

	@Basic
	@Column(name = "street")
	private String street;

	@OneToOne
	@JoinColumn(name = "user_id")
	private User user;
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public UUID getAddressId() {
		return addressId;
	}

	public void setAddressId(UUID addressId) {
		this.addressId = addressId;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}
}
