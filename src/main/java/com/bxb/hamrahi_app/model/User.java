package com.bxb.hamrahi_app.model;

import com.bxb.hamrahi_app.util.UserRole;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

/** Entity representing a user in the system. */
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(unique = true, nullable = false)
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    private UserRole userRole;

    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}