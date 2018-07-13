package com.example.hibernate.relationship.manytomany.withextracolumn.compositekey;

import static org.junit.Assert.*;

import java.util.Set;
import java.util.UUID;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.example.hibernate.BaseTest;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PatientAllergyTest extends BaseTest {

	private static UUID patientId;
	
	@Test
	public void aCreatePatientWithAllergyTest() {
		Allergy allergy = new Allergy("dust");
		em.persist(allergy);

		Allergy allergy1 = new Allergy("asdadasdt");
		em.persist(allergy1);
		
		Allergy allergy2 = new Allergy("asdadsfjkjljklasdasd");
		em.persist(allergy2);
		
		Patient patient = new Patient();
		patient.setName("Amit Patil");
		em.persist(patient);
		patientId = patient.getPatientId();

		PatientAllergy patientAllergy = new PatientAllergy(patient, allergy);
		patient.getPatientAllergies().add(patientAllergy);
		em.persist(patientAllergy);
		
		PatientAllergy patientAllergy1 = new PatientAllergy(patient, allergy1);
		patient.getPatientAllergies().add(patientAllergy1);
		em.persist(patientAllergy1);
		
		PatientAllergy patientAllergy2 = new PatientAllergy(patient, allergy2);
		patient.getPatientAllergies().add(patientAllergy2);
		em.persist(patientAllergy2);
	}
	
	@Test
	public void bGetpatientAllergiesTest() {
		Patient patient = em.find(Patient.class, patientId);
		assertNotNull(patient);
		assertTrue(em.contains(patient));

		Set<PatientAllergy> patientAllergies = patient.getPatientAllergies();
		assertNotNull(patientAllergies);
		assertEquals(3, patientAllergies.size());
	}

}
