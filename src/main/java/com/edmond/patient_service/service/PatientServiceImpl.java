package com.edmond.patient_service.service;

import com.edmond.patient_service.dto.PatientRequestDTO;
import com.edmond.patient_service.dto.PatientResponseDTO;
import com.edmond.patient_service.exception.EmailAlreadyExistsException;
import com.edmond.patient_service.exception.PatientNotFoundException;
import com.edmond.patient_service.mapper.PatientMapper;
import com.edmond.patient_service.model.Patient;
import com.edmond.patient_service.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PatientServiceImpl implements PatientService {

	private final PatientRepository patientRepository;

	@Override
	public List<PatientResponseDTO> getPatients() {
		List<Patient> patients = patientRepository.findAll();
		List<PatientResponseDTO> patientResponseDTOS = patients.stream().map(PatientMapper::toDTO).toList();
		return patientResponseDTOS;
	}

	@Override
	public PatientResponseDTO createPatient(PatientRequestDTO patientRequestDTO) {

		if (patientRepository.existsByEmail(patientRequestDTO.getEmail())) {
			throw new EmailAlreadyExistsException("Email already exists");
		}

		Patient newPatient = patientRepository.save(PatientMapper.toModel(patientRequestDTO));
		return PatientMapper.toDTO(newPatient);
	}

	@Override
	public PatientResponseDTO updatePatient(UUID id, PatientRequestDTO patientRequestDTO) {
		
		Patient patient = patientRepository.findById(id)
				.orElseThrow(() -> new PatientNotFoundException("Patient not found"));
		
		if (patientRepository.existsByEmailAndIdNot(patientRequestDTO.getEmail(),id)) {
			throw new EmailAlreadyExistsException("Email already exists");
		}
		
		patient.setName(patientRequestDTO.getName());
		patient.setAddress(patientRequestDTO.getAddress());
		patient.setEmail(patientRequestDTO.getEmail());
		patient.setDateOfBirth(LocalDate.parse(patientRequestDTO.getDateOfBirth()));
		
		Patient updatePatient = patientRepository.save(patient);
		
		return PatientMapper.toDTO(updatePatient);
	}
}
