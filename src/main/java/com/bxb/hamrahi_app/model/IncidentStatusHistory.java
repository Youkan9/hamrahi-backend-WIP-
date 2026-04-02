package com.bxb.hamrahi_app.model;


import com.bxb.hamrahi_app.util.IncidentStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "incident_status_history")
@Getter
@Setter
public class IncidentStatusHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Incident incident;

    @Enumerated(EnumType.STRING)
    private IncidentStatus status;

    private Long updatedBy;

    private String notes;

    private LocalDateTime createdAt;
}