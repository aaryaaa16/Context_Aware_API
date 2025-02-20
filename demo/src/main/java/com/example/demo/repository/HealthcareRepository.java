package com.example.demo.repository;

import com.example.demo.model.tables.HealthcareTable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HealthcareRepository extends JpaRepository<HealthcareTable, Long> {
    Optional<HealthcareTable> findByIndex(String index);
}
