package com.example.PulseCheck.monitor.repo;

import com.example.PulseCheck.monitor.model.Website;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface MonitorRepo extends JpaRepository<Website, UUID> {
}
