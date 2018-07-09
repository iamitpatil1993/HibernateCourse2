package com.example.hibernate.relationship.singlevalue;

import java.util.UUID;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.example.hibernate.BaseEntity;
import com.example.hibernate.relationship.singlevalue.Address;

@Entity
@Table(name = "one_to_one_user_1")
public class User extends BaseEntity {

	private static final long serialVersionUID = 7687953445493889254L;

	@Basic
	@Id
	@GeneratedValue(generator = "user_id_gen")
	@GenericGenerator(name = "user_id_gen", strategy = "uuid2")
	private UUID userId;

	@Basic
	@Column(name = "username")
	private String username;

	@OneToOne(mappedBy = "user", cascade = { CascadeType.PERSIST }, fetch = FetchType.LAZY)
	private Address address;

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public User(String username) {
		this.username = username;
	}

	public User() {
	}

	public UUID getUserId() {
		return userId;
	}

	public void setUserId(UUID userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
}
