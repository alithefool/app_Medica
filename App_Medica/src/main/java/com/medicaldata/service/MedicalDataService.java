package com.medicaldata.service;

import com.medicaldata.model.relational.Patient;
import com.medicaldata.model.relational.MedicalRecord;
import com.medicaldata.model.nosql.UnstructuredMedicalData;
import com.medicaldata.repository.relational.PatientRepository;
import com.medicaldata.repository.relational.MedicalRecordRepository;
import com.medicaldata.repository.nosql.UnstructuredDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.Map;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.io.IOException;

@Service
public class MedicalDataService {
    
    private final PatientRepository patientRepository;
    private final MedicalRecordRepository medicalRecordRepository;
    private final UnstructuredDataRepository unstructuredDataRepository;
    
    @Autowired
    public MedicalDataService(
            PatientRepository patientRepository,
            MedicalRecordRepository medicalRecordRepository,
            UnstructuredDataRepository unstructuredDataRepository) {
        this.patientRepository = patientRepository;
        this.medicalRecordRepository = medicalRecordRepository;
        this.unstructuredDataRepository = unstructuredDataRepository;
    }
    
    /* === Operaciones de pacientes === */
    
    public Patient registerPatient(Patient patient) {
        return patientRepository.save(patient);
    }
    
    public Optional<Patient> getPatientById(Long id) {
        return patientRepository.findById(id);
    }
    
    public Patient getPatientByDocumentNumber(String documentNumber) {
        return patientRepository.findByDocumentNumber(documentNumber);
    }
    
    public List<Patient> searchPatientsByLastName(String lastName) {
        return patientRepository.findByLastNameContainingIgnoreCase(lastName);
    }
    
    /* === Operaciones de historias clínicas === */
    
    @Transactional
    public MedicalRecord createMedicalRecord(MedicalRecord record) {
        return medicalRecordRepository.save(record);
    }
    
    public List<MedicalRecord> getPatientMedicalHistory(Long patientId) {
        return medicalRecordRepository.findByPatientId(patientId);
    }
    
    public List<MedicalRecord> searchMedicalRecordsByTerm(String term) {
        return medicalRecordRepository.searchByTerm(term);
    }
    
    /* === Operaciones con datos no estructurados === */
    
    @Transactional
    public UnstructuredMedicalData storeUnstructuredData(
            Long patientId, 
            Long medicalRecordId,
            MultipartFile file, 
            String dataType,
            Map<String, Object> metadata,
            List<String> tags,
            String uploadedBy) throws IOException {
        
        UnstructuredMedicalData data = new UnstructuredMedicalData();
        data.setPatientId(patientId);
        data.setMedicalRecordId(medicalRecordId);
        data.setDataType(dataType);
        data.setContentType(file.getContentType());
        data.setContent(file.getBytes());
        data.setUploadDate(LocalDateTime.now());
        data.setUploadedBy(uploadedBy);
        data.setMetadata(metadata);
        data.setTags(tags);
        
        UnstructuredMedicalData savedData = unstructuredDataRepository.save(data);
        
        // Actualizar referencia en el historial médico si existe
        if (medicalRecordId != null) {
            medicalRecordRepository.findById(medicalRecordId).ifPresent(record -> {
                record.setUnstructuredDataId(savedData.getId());
                medicalRecordRepository.save(record);
            });
        }
        
        return savedData;
    }
    
    public List<UnstructuredMedicalData> getPatientUnstructuredData(Long patientId) {
        return unstructuredDataRepository.findByPatientId(patientId);
    }
    
    public List<UnstructuredMedicalData> getUnstructuredDataByTags(List<String> tags) {
        return unstructuredDataRepository.findByAllTags(tags);
    }
    
    /* === Análisis y consultas avanzadas === */
    
    public List<Patient> findPatientsByDiagnosis(String diagnosis) {
        return patientRepository.findByDiagnosisContaining(diagnosis);
    }
    
    public List<MedicalRecord> findMedicalRecordsByDateRange(LocalDate startDate, LocalDate endDate) {
        return medicalRecordRepository.findByRecordDateBetween(startDate, endDate);
    }
}