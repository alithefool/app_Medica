package com.medicaldata.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@Configuration
@EnableJpaRepositories(basePackages = "com.medicaldata.repository.relational")
@EnableMongoRepositories(basePackages = "com.medicaldata.repository.nosql")
@EntityScan(basePackages = "com.medicaldata.model")
public class DatabaseConfig {

}
