package com.edmond.patient_service.controller;

import com.edmond.patient_service.dto.PatientRequestDTO;
import com.edmond.patient_service.dto.PatientResponseDTO;
import com.edmond.patient_service.dto.validators.CreatePatientValidationGroup;
import com.edmond.patient_service.service.PatientService;

import jakarta.validation.Valid;
import jakarta.validation.groups.Default;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/patients")
@RequiredArgsConstructor
public class PatientController {

	private final PatientService patientService;

	@GetMapping
	public ResponseEntity<List<PatientResponseDTO>> getPatients() {
		List<PatientResponseDTO> patients = patientService.getPatients();
		return patients.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(patients);
	}

	@PostMapping
	public ResponseEntity<PatientResponseDTO> createPatient(@Validated({ Default.class,
			CreatePatientValidationGroup.class }) @RequestBody PatientRequestDTO patientRequestDTO) {
		PatientResponseDTO patientResponseDTO = patientService.createPatient(patientRequestDTO);
		return ResponseEntity.ok().body(patientResponseDTO);
	}

	@PutMapping("/{id}")
	public ResponseEntity<PatientResponseDTO> updatePatient(@PathVariable("id") UUID id,
			@Validated({ Default.class }) @RequestBody PatientRequestDTO patientRequestDTO) {
		PatientResponseDTO patientResponseDTO = patientService.updatePatient(id, patientRequestDTO);
		return ResponseEntity.ok().body(patientResponseDTO);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> deletePatient(@PathVariable("id") UUID id) {
		patientService.deletePatient(id);
		return ResponseEntity.ok("Patient deleted successfully");
	}

}
