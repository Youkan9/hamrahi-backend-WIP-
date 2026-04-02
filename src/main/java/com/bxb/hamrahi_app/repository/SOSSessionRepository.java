package com.bxb.hamrahi_app.repository;

import com.bxb.hamrahi_app.model.SOSSession;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SOSSessionRepository extends JpaRepository<SOSSession, Long> {
}