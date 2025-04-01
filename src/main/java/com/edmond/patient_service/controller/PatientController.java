package com.edmond.patient_service.controller;

import com.edmond.patient_service.dto.PatientRequestDTO;
import com.edmond.patient_service.dto.PatientResponseDTO;
import com.edmond.patient_service.service.PatientService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/patients")
@RequiredArgsConstructor
public class PatientController {

    private final PatientService patientService;

    @GetMapping
    public ResponseEntity<List<PatientResponseDTO>> getPatients(){
        List<PatientResponseDTO> patients = patientService.getPatients();
        return patients.isEmpty() 
                ? ResponseEntity.noContent().build() 
                : ResponseEntity.ok(patients);
    }
    
    @PostMapping
    public ResponseEntity<PatientResponseDTO> createPatient(@Valid @RequestBody PatientRequestDTO patientRequestDTO){
    	PatientResponseDTO patientResponseDTO = patientService.createPatient(patientRequestDTO);
    	return ResponseEntity.ok().body(patientResponseDTO);
    }

}
