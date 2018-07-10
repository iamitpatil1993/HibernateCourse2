package com.example.hibernate.relationship.onetomany;

import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
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
@Table(name = "person")
public class Person extends BaseEntity {

	private static final long serialVersionUID = 146340135909356369L;

	@Basic
	@Id
	@GeneratedValue(generator = "person_id_gen")
	@GenericGenerator(name = "person_id_gen", strategy = "uuid2")
	@Column(name = "person_id")
	private UUID personId;

	public Person() {
		super();
	}

	@Basic
	@Column(name = "full_name")
	private String fullName;

	@OneToMany(mappedBy = "person", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	private Collection<Interest> interests = new ArrayList<>();
	
	public Collection<Interest> getInterests() {
		return interests;
	}

	public void setInterests(Collection<Interest> interests) {
		this.interests = interests;
	}

	public Person(String fullName) {
		super();
		this.fullName = fullName;
	}

	public UUID getPersonId() {
		return personId;
	}

	public void setPersonId(UUID personId) {
		this.personId = personId;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public void addInterest(Interest interest) {
		this.interests.add(interest);
		interest.setPerson(this);
	}
}
