package com.medicaldata.repository.nosql;

import com.medicaldata.model.nosql.UnstructuredMedicalData;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import java.util.List;

public interface UnstructuredDataRepository extends MongoRepository<UnstructuredMedicalData, String> {
    List<UnstructuredMedicalData> findByPatientId(Long patientId);
    List<UnstructuredMedicalData> findByMedicalRecordId(Long medicalRecordId);
    List<UnstructuredMedicalData> findByDataType(String dataType);
    
    @Query("{ 'tags': { $all: ?0 } }")
    List<UnstructuredMedicalData> findByAllTags(List<String> tags);
    
    @Query("{ 'metadata.?0': ?1 }")
    List<UnstructuredMedicalData> findByMetadata(String key, Object value);
}