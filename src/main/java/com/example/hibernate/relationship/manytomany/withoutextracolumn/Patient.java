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
@Table(name = "patient_1")
public class Patient extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6420163456783389279L;

	@Basic
	@Id
	@GeneratedValue(generator = "patient_id_gen")
	@GenericGenerator(name = "patient_id_gen", strategy = "uuid2")
	@Column(name = "patient_id")
	private UUID patientId;

	@Column(name = "name")
	private String name;

	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "patients")
	Set<Allergy> allergies = new HashSet<>();

	public UUID getPatientId() {
		return patientId;
	}

	public void setPatientId(UUID patientId) {
		this.patientId = patientId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((patientId == null) ? 0 : patientId.hashCode());
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
		if (!(obj instanceof Patient)) {
			return false;
		}
		Patient other = (Patient) obj;
		if (patientId == null) {
			return false;
		}
		return patientId.equals(other.patientId);
	}

	public void addAllergy(Allergy allergy) {
		if (allergy != null) {
			allergy.getPatients().add(this);
			allergies.add(allergy);
		}
	}

	public Set<Allergy> getAllergies() {
		return allergies;
	}

	public void setAllergies(Set<Allergy> allergies) {
		this.allergies = allergies;
	}
	
	
}
