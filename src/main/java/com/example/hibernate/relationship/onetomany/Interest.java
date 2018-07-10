package com.example.hibernate.relationship.onetomany;

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

@Entity
@Table(name = "interest")
public class Interest extends BaseEntity {

	private static final long serialVersionUID = -6798436006586970802L;

	@Basic
	@Id
	@GeneratedValue(generator = "interest_id_gen")
	@GenericGenerator(name = "interest_id_gen", strategy = "uuid2")
	private UUID interestId;
	
	@Basic
	@Column(name = "interest")
	private String interest;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "person_id")
	private Person person;

	public Person getPerson() {
		return person;
	}

	public Interest() {
		super();
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public UUID getInterestId() {
		return interestId;
	}

	public void setInterestId(UUID interestId) {
		this.interestId = interestId;
	}

	public String getInterest() {
		return interest;
	}

	public void setInterest(String interest) {
		this.interest = interest;
	}

	public Interest(String interest) {
		super();
		this.interest = interest;
	}
}
