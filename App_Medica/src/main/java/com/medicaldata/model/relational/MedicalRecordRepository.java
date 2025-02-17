package com.medicaldata.repository.relational;

import com.medicaldata.model.relational.MedicalRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.time.LocalDate;
import java.util.List;

public interface MedicalRecordRepository extends JpaRepository<MedicalRecord, Long> {
    List<MedicalRecord> findByPatientId(Long patientId);
    List<MedicalRecord> findByDoctorId(Long doctorId);
    List<MedicalRecord> findByRecordDateBetween(LocalDate startDate, LocalDate endDate);
    
    @Query("SELECT mr FROM MedicalRecord mr WHERE mr.diagnosis LIKE %:term% OR mr.treatment LIKE %:term% OR mr.observations LIKE %:term%")
    List<MedicalRecord> searchByTerm(String term);
}