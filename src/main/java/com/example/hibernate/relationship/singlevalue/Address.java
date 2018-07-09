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
