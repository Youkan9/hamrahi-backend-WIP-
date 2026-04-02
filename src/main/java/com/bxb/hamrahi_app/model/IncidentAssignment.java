package com.bxb.hamrahi_app.model;

import com.bxb.hamrahi_app.util.IncidentStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "incident_assignments")
@Getter
@Setter
public class IncidentAssignment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Incident incident;

    @ManyToOne
    private Department department;

    private Long assignedBy;

    private LocalDateTime assignedAt;
    private LocalDateTime resolvedAt;

    @Enumerated(EnumType.STRING)
    private IncidentStatus status;
}