package com.bxb.hamrahi_app.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "safety_companion_sessions")
@Getter
@Setter
public class SafetyCompanionSession {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;

    private LocalDateTime startTime;
    private LocalDateTime endTime;

    private Double startLat;
    private Double startLng;

    private Double endLat;
    private Double endLng;

    private Boolean sosTriggered;

    @OneToMany(mappedBy = "safetySession")
    private List<SOSSession> sosSessions = new ArrayList<>();

    private LocalDateTime createdAt;
}