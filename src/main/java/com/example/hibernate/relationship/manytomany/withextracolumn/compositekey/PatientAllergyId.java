package com.example.hibernate.relationship.manytomany.withextracolumn.compositekey;

import java.io.Serializable;
import java.util.UUID;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Embeddable;

import com.example.hibernate.BaseEntity;

@Embeddable
@Access(AccessType.FIELD)
public class PatientAllergyId implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4102312038357966317L;

	@Column(name = "patient_id")
	private UUID patientId;

	@Column(name = "allergy_id")
	private UUID allergyId;
	
	public PatientAllergyId(UUID patientId, UUID allergyId) {
		super();
		this.patientId = patientId;
		this.allergyId = allergyId;
	}

	public PatientAllergyId() {
		super();
	}

	public UUID getPatientId() {
		return patientId;
	}

	public void setPatientId(UUID patientId) {
		this.patientId = patientId;
	}

	public UUID getAllergyId() {
		return allergyId;
	}

	public void setAllergyId(UUID allergyId) {
		this.allergyId = allergyId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((allergyId == null) ? 0 : allergyId.hashCode());
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
		if (!(obj instanceof PatientAllergyId)) {
			return false;
		}
		PatientAllergyId other = (PatientAllergyId) obj;
		if (allergyId == null) {
			if (other.allergyId != null) {
				return false;
			}
		} else if (!allergyId.equals(other.allergyId)) {
			return false;
		}
		if (patientId == null) {
			if (other.patientId != null) {
				return false;
			}
		} else if (!patientId.equals(other.patientId)) {
			return false;
		}
		return true;
	}
}
