package com.edmond.patient_service.service;

import com.edmond.patient_service.dto.PatientRequestDTO;
import com.edmond.patient_service.dto.PatientResponseDTO;
import com.edmond.patient_service.exception.EmailAlreadyExistsException;
import com.edmond.patient_service.mapper.PatientMapper;
import com.edmond.patient_service.model.Patient;
import com.edmond.patient_service.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

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
}
