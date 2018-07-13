package com.example.hibernate.relationship.manytomany.withextracolumn.compositekey;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

import com.example.hibernate.BaseEntity;

@Entity
@Table(name = "patient_allergy_2")
public class PatientAllergy extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1977471745944325749L;

	@EmbeddedId
	private PatientAllergyId id;

	@ManyToOne(fetch = FetchType.LAZY)
	@MapsId(value = "patientId")
	private Patient patient;

	@ManyToOne(fetch = FetchType.LAZY)
	@MapsId(value = "allergyId")
	private Allergy allergy;

	public PatientAllergy(Patient patient, Allergy allergy) {
		this.patient = patient;
		this.allergy = allergy;
		this.id = new PatientAllergyId(patient.getPatientId(), allergy.getAllergyId());
	}

	public PatientAllergy() {
		super();
	}
	
	public PatientAllergyId getId() {
		return id;
	}

	public void setId(PatientAllergyId id) {
		this.id = id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		if (!(obj instanceof PatientAllergy)) {
			return false;
		}
		PatientAllergy other = (PatientAllergy) obj;
		if (id == null) {
			return false;
		}
		return id.equals(other.getId());
	}
}
