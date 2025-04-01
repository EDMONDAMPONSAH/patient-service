package com.edmond.patient_service.repository;

import com.edmond.patient_service.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PatientRepository extends JpaRepository<Patient, UUID> {
	
	boolean existsByEmail(String email);
	
}
