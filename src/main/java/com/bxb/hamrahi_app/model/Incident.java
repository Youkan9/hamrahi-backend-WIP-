package com.bxb.hamrahi_app.model;


import com.bxb.hamrahi_app.util.IncidentCategory;
import com.bxb.hamrahi_app.util.IncidentStatus;
import com.bxb.hamrahi_app.util.RecommendedAction;
import com.bxb.hamrahi_app.util.SeverityLevel;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "incidents")
@Getter
@Setter
public class Incident {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;

    @Enumerated(EnumType.STRING)
    private IncidentCategory category;

    private String imageUrl;
    private String voiceUrl;

    @OneToMany(mappedBy = "incident", cascade = CascadeType.ALL)
    private List<IncidentMedia> media = new ArrayList<>();

    private String comment;

    private Double latitude;
    private Double longitude;

    @Enumerated(EnumType.STRING)
    private SeverityLevel severity;

    @Enumerated(EnumType.STRING)
    private RecommendedAction recommendedAction;

    @Enumerated(EnumType.STRING)
    private IncidentStatus status;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "incident")
    private List<AIAnalysis> analyses = new ArrayList<>();
}