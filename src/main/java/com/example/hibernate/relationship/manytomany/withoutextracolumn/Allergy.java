package com.example.hibernate.relationship.manytomany.withoutextracolumn;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.example.hibernate.BaseEntity;

@Entity
@Table(name = "allergy_1")
public class Allergy extends BaseEntity {

	private static final long serialVersionUID = 6704579497223893398L;

	@Basic
	@Id
	@GeneratedValue(generator = "allergy_id_gen")
	@GenericGenerator(name = "allergy_id_gen", strategy = "uuid2")
	@Column(name = "allergy_id")
	private UUID allergyId;

	@Column(name = "allergy")
	private String allergyValue;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(inverseJoinColumns = { @JoinColumn(name = "patient_id") }, joinColumns = {
			@JoinColumn(name = "allergy_id") }, name = "patient_allergy_1")
	Set<Patient> patients = new HashSet<>();

	public Allergy(String allergyValue) {
		super();
		this.allergyValue = allergyValue;
	}

	public Allergy() {
		super();
	}

	public Set<Patient> getPatients() {
		return patients;
	}

	public void setPatients(Set<Patient> patients) {
		this.patients = patients;
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
