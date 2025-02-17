package com.medicaldata.web;

import com.medicaldata.model.relational.Patient;
import com.medicaldata.model.relational.MedicalRecord;
import com.medicaldata.model.nosql.UnstructuredMedicalData;
import com.medicaldata.service.MedicalDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.io.IOException;

@RestController
@RequestMapping("/api/v1")
public class MedicalDataController {
    
    private final MedicalDataService medicalDataService;
    
    @Autowired
    public MedicalDataController(MedicalDataService medicalDataService) {
        this.medicalDataService = medicalDataService;
    }
    
    /* === Endpoints de Pacientes === */
    
    @PostMapping("/patients")
    public ResponseEntity<Patient> registerPatient(@RequestBody Patient patient) {
        Patient savedPatient = medicalDataService.registerPatient(patient);
        return new ResponseEntity<>(savedPatient, HttpStatus.CREATED);
    }
    
    @GetMapping("/patients/{id}")
    public ResponseEntity<Patient> getPatientById(@PathVariable Long id) {
        Optional<Patient> patient = medicalDataService.getPatientById(id);
        return patient.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
    
    @GetMapping("/patients/search")
    public ResponseEntity<List<Patient>> searchPatients(
            @RequestParam(required = false) String lastName,
            @RequestParam(required = false) String documentNumber,
            @RequestParam(required = false) String diagnosis) {
        
        List<Patient> patients;
        
        if (documentNumber != null) {
            Patient patient = medicalDataService.getPatientByDocumentNumber(documentNumber);
            patients = patient != null ? List.of(patient) : List.of();
        } else if (diagnosis != null) {
            patients = medicalDataService.findPatientsByDiagnosis(diagnosis);
        } else if (lastName != null) {
            patients = medicalDataService.searchPatientsByLastName(lastName);
        } else {
            return ResponseEntity.badRequest().build();
        }
        
        return ResponseEntity.ok(patients);
    }
    
    /* === Endpoints de Historiales Médicos === */
    
    @PostMapping("/medical-records")
    public ResponseEntity<MedicalRecord> createMedicalRecord(@RequestBody MedicalRecord record) {
        MedicalRecord savedRecord = medicalDataService.createMedicalRecord(record);
        return new ResponseEntity<>(savedRecord, HttpStatus.CREATED);
    }
    
    @GetMapping("/patients/{patientId}/medical-records")
    public ResponseEntity<List<MedicalRecord>> getPatientMedicalHistory(@PathVariable Long patientId) {
        List<MedicalRecord> records = medicalDataService.getPatientMedicalHistory(patientId);
        return ResponseEntity.ok(records);
    }
    
    @GetMapping("/medical-records/search")
    public ResponseEntity<List<MedicalRecord>> searchMedicalRecords(
            @RequestParam(required = false) String term,
            @RequestParam(required = false) LocalDate startDate,
            @RequestParam(required = false) LocalDate endDate) {
        
        List<MedicalRecord> records;
        
        if (term != null) {
            records = medicalDataService.searchMedicalRecordsByTerm(term);
        } else if (startDate != null && endDate != null) {
            records = medicalDataService.findMedicalRecordsByDateRange(startDate, endDate);
        } else {
            return ResponseEntity.badRequest().build();
        }
        
        return ResponseEntity.ok(records);
    }
    
    /* === Endpoints de Datos No Estructurados === */
    
    @PostMapping("/patients/{patientId}/unstructured-data")
    public ResponseEntity<UnstructuredMedicalData> uploadUnstructuredData(
            @PathVariable Long patientId,
            @RequestParam(required = false) Long medicalRecordId,
            @RequestParam("file") MultipartFile file,
            @RequestParam String dataType,
            @RequestParam(required = false) Map<String, Object> metadata,
            @RequestParam(required = false) List<String> tags,
            @RequestParam String uploadedBy) {
        
        try {
            UnstructuredMedicalData savedData = medicalDataService.storeUnstructuredData(
                    patientId, medicalRecordId, file, dataType, metadata, tags, uploadedBy);
            return new ResponseEntity<>(savedData, HttpStatus.CREATED);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @GetMapping("/patients/{patientId}/unstructured-data")
    public ResponseEntity<List<UnstructuredMedicalData>> getPatientUnstructuredData(@PathVariable Long patientId) {
        List<UnstructuredMedicalData> data = medicalDataService.getPatientUnstructuredData(patientId);
        return ResponseEntity.ok(data);
    }
    
    @GetMapping("/unstructured-data/search")
    public ResponseEntity<List<UnstructuredMedicalData>> searchUnstructuredData(
            @RequestParam List<String> tags) {
        
        List<UnstructuredMedicalData> data = medicalDataService.getUnstructuredDataByTags(tags);
        return ResponseEntity.ok(data);
    }
}