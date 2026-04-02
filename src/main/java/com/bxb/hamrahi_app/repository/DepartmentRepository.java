package com.bxb.hamrahi_app.repository;

import com.bxb.hamrahi_app.model.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Department, Long> {
}