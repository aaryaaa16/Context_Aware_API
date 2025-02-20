package com.example.demo.repository;

import com.example.demo.model.tables.IdentityTable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IdentityRepository extends JpaRepository<IdentityTable, Long> {
    Optional<IdentityTable> findByIndex(String index);
}