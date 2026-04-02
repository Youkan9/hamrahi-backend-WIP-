package com.bxb.hamrahi_app.model;


import com.bxb.hamrahi_app.util.RecommendedAction;
import com.bxb.hamrahi_app.util.SOSStatus;
import com.bxb.hamrahi_app.util.SeverityLevel;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;


@Entity
@Table(name = "sos_sessions")
@Getter
@Setter
public class SOSSession {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;

    @ManyToOne
    @JoinColumn(name = "safety_session_id")
    private SafetyCompanionSession safetySession;

    private Double latitude;
    private Double longitude;

    private LocalDateTime startTime;
    private LocalDateTime endTime;

    @Column(columnDefinition = "TEXT")
    private String transcript;

    @Enumerated(EnumType.STRING)
    private SeverityLevel severityDetected;

    @Enumerated(EnumType.STRING)
    private RecommendedAction recommendedAction;

    @Enumerated(EnumType.STRING)
    private SOSStatus status;

    private LocalDateTime createdAt;
}