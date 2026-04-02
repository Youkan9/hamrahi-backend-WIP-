package com.bxb.hamrahi_app.repository;

import com.bxb.hamrahi_app.model.IncidentStatusHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IncidentStatusHistoryRepository extends JpaRepository<IncidentStatusHistory, Long> {
}