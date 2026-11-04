package com.example.PulseCheck.monitor.model;

import com.example.PulseCheck.monitor.enums.CurrentStatus;
import com.example.PulseCheck.monitor.enums.MonitorStatus;
import com.example.PulseCheck.user.entity.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "monitors")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Website {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false, length = 2048)
    private String url;

    @Column(nullable = false)
    private Integer intervalInSecond;

    @Column(nullable = false)
    private Integer timeoutSecond;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private MonitorStatus status=MonitorStatus.ACTIVE;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private CurrentStatus currentState=CurrentStatus.UNKNOWN;

    private LocalDateTime lastCheckedAt;

    private LocalDateTime nextCheckAt;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime updatedAt;
}
