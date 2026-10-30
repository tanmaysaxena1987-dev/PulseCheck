package com.example.PulseCheck.user.entity;

import com.example.PulseCheck.user.enums.Role;
import com.example.PulseCheck.user.enums.Status;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users",
        indexes = {
        @Index(name = "idx_user_email",columnList = "email",unique = true)
        })
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(nullable = false,length = 100)
    private String name;
    @Column(nullable = false, length = 100)
    @Email
    private String email;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;
    private boolean emailVerified=false;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;
    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;
    private LocalDateTime lastLoginAt;
}
