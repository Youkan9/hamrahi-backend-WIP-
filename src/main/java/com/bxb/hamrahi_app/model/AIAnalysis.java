package com.bxb.hamrahi_app.model;

import com.bxb.hamrahi_app.util.IncidentCategory;
import com.bxb.hamrahi_app.util.RecommendedAction;
import com.bxb.hamrahi_app.util.SeverityLevel;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "ai_analysis")
public class AIAnalysis {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Incident incident;

    private Double confidenceScore;

    @Enumerated(EnumType.STRING)
    private IncidentCategory category;

    @Enumerated(EnumType.STRING)
    private SeverityLevel severity;

    @Enumerated(EnumType.STRING)
    private RecommendedAction recommendedAction;

    @Column(columnDefinition = "TEXT")
    private String rawOutput;

    private LocalDateTime processedAt;
}
