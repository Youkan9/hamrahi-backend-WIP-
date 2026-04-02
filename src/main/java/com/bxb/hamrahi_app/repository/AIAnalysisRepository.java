package com.bxb.hamrahi_app.repository;

import com.bxb.hamrahi_app.model.AIAnalysis;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AIAnalysisRepository extends JpaRepository<AIAnalysis, Long> {
}
