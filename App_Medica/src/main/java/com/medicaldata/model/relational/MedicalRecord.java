package com.medicaldata.model.relational;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "medical_records")
public class MedicalRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;
    
    @Column(nullable = false)
    private LocalDate recordDate;
    
    private String diagnosis;
    
    private String treatment;
    
    private String observations;
    
    @ManyToOne
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;
    
    @Column(name = "unstructured_data_id")
    private String unstructuredDataId; // Referencia al documento en MongoDB
    
    // Getters and setters
    // (Agrega aquí todos los getters y setters)
}