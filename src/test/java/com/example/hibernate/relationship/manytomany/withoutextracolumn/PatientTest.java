package com.example.hibernate.relationship.manytomany.withoutextracolumn;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Set;
import java.util.UUID;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.example.hibernate.BaseTest;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PatientTest extends BaseTest {

	private static UUID patientId = null;
	
	@Test
	public void aCreatePatientWithAllergyTest() {

		Allergy allergy = new Allergy("Dust");
		em.persist(allergy);
		
		Allergy allergy1 = new Allergy("Perfume");
		em.persist(allergy1);

		Allergy allergy2 = new Allergy("Oil");
		em.persist(allergy2);
		
		Patient patient = new Patient();
		patient.setName("Amit Patil");
		em.persist(patient);
		patientId = patient.getPatientId();
		
		patient.addAllergy(allergy);
		patient.addAllergy(allergy1);
		patient.addAllergy(allergy2);
		
		// adding duplicate to check equals() and hashcode() works
		patient.addAllergy(allergy);
		patient.addAllergy(allergy1);
		patient.addAllergy(allergy2);
	}
	
	@Test
	public void bGetPatientAllergiesTest() {
		Patient patient = em.find(Patient.class, patientId);
		assertNotNull(patient);
		assertTrue(em.contains(patient));
		
		Set<Allergy> allergies = patient.getAllergies();
		assertNotNull(allergies);
		assertEquals(3, allergies.size());
	}
}
