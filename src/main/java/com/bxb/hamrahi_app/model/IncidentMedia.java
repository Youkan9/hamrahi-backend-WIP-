package com.bxb.hamrahi_app.model;

import com.bxb.hamrahi_app.util.MediaType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "incident_media")
@Getter
@Setter
public class IncidentMedia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "incident_id")
    private Incident incident;

    private String filePath;

    @Enumerated(EnumType.STRING)
    private MediaType type;

    private LocalDateTime createdAt;
}