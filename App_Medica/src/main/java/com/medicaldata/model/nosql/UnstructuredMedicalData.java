package com.medicaldata.model.nosql;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.List;

@Document(collection = "unstructured_data")
public class UnstructuredMedicalData {
    @Id
    private String id;
    
    private Long patientId; // Referencia al ID del paciente en PostgreSQL
    private Long medicalRecordId; // Referencia al ID del historial en PostgreSQL
    
    private String dataType; // Tipo de datos: "IMAGE", "DOCUMENT", "NOTE", etc.
    private String contentType; // Tipo MIME: "image/jpeg", "application/pdf", etc.
    private String filePath; // Para archivos grandes almacenados externamente
    private byte[] content; // Para archivos pequeños almacenados en la BD
    
    private LocalDateTime uploadDate;
    private String uploadedBy;
    
    private Map<String, Object> metadata; // Metadatos flexibles según el tipo
    private List<String> tags; // Etiquetas para búsqueda
    
    // Getters and setters
    // (Agrega aquí todos los getters y setters)
}