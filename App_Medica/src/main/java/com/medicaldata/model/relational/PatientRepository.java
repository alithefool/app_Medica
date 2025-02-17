package com.medicaldata.repository.relational;

import com.medicaldata.model.relational.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface PatientRepository extends JpaRepository<Patient, Long> {
    Patient findByDocumentNumber(String documentNumber);
    List<Patient> findByLastNameContainingIgnoreCase(String lastName);
    
    @Query("SELECT p FROM Patient p JOIN p.medicalRecords mr WHERE mr.diagnosis LIKE %:diagnosis%")
    List<Patient> findByDiagnosisContaining(String diagnosis);
}