package com.edmond.patient_service.service;

import com.edmond.patient_service.dto.PatientRequestDTO;
import com.edmond.patient_service.dto.PatientResponseDTO;

import java.util.List;
import java.util.UUID;

public interface PatientService {

    public List<PatientResponseDTO> getPatients();
    
    public PatientResponseDTO createPatient(PatientRequestDTO patientRequestDTO);
    
    public PatientResponseDTO updatePatient(UUID id, PatientRequestDTO patientRequestDTO);

}
