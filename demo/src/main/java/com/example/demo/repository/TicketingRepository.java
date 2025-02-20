package com.example.demo.repository;

import com.example.demo.model.tables.TicketTable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TicketingRepository extends JpaRepository<TicketTable, Long> {
    Optional<TicketTable> findByIndex(String index);
}
