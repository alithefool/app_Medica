package com.medicaldata.model.relational;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "doctors")
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String firstName;
    
    @Column(nullable = false)
    private String lastName;
    
    @Column(nullable = false, unique = true)
    private String licenseNumber;
    
    private String specialty;
    
    private String contactPhone;
    
    private String email;
    
    @OneToMany(mappedBy = "doctor")
    private List<MedicalRecord> medicalRecords;
    
}
