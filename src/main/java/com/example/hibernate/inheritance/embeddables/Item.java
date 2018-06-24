package com.example.hibernate.inheritance.embeddables;

import java.io.Serializable;
import java.util.UUID;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "item")
public class Item implements Serializable {

	private static final long serialVersionUID = -3672720532777978845L;

	@Basic
	@Id
	@GeneratedValue(generator = "item_id_gen")
	@GenericGenerator(name = "item_id_gen", strategy = "uuid2")
	@Column(name = "item_id")
	private UUID itemId;
	
	// U can't use Measurement type here u mustuse actual subclass type
	@Embedded 
	private Weight weight;

	// U can't use Measurement type here u mustuse actual subclass type
	@Embedded
	private Height height;

	public UUID getItemId() {
		return itemId;
	}

	public void setItemId(UUID itemId) {
		this.itemId = itemId;
	}

	public Weight getWeight() {
		return weight;
	}

	public void setWeight(Weight weight) {
		this.weight = weight;
	}

	public Height getHeight() {
		return height;
	}

	public void setHeight(Height height) {
		this.height = height;
	}
}
