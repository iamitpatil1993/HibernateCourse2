package com.example.hibernate.relationship.manytomany.withextracolumn.compositekey;

import java.util.UUID;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.example.hibernate.BaseEntity;

@Entity
@Table(name = "allergy_2")
public class Allergy extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3611498161698917536L;

	@Basic
	@Id
	@GeneratedValue(generator = "allergy_id_gen_1")
	@GenericGenerator(name = "allergy_id_gen_1", strategy = "uuid2")
	@Column(name = "allergy_id")
	private UUID allergyId;

	@Column(name = "allergy")
	private String allergyValue;

	public Allergy(String allergyValue) {
		super();
		this.allergyValue = allergyValue;
	}

	public Allergy() {
		super();
	}

	public UUID getAllergyId() {
		return allergyId;
	}

	public void setAllergyId(UUID allergyId) {
		this.allergyId = allergyId;
	}

	public String getAllergyValue() {
		return allergyValue;
	}

	public void setAllergyValue(String allergyValue) {
		this.allergyValue = allergyValue;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((allergyId == null) ? 0 : allergyId.hashCode());
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
		if (!(obj instanceof Allergy)) {
			return false;
		}
		Allergy other = (Allergy) obj;
		if (this.getAllergyId() == null) {
			return false;
		}
		return this.getAllergyId().equals(other.getAllergyId());
	}
}
