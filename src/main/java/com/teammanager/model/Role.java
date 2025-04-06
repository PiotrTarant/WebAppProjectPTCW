package com.teammanager.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private RoleName name;

    public Role(RoleName name) {
        this.name = name;
    }
}

@Enumerated(EnumType.STRING)
public enum RoleName {
    ROLE_USER,
    ROLE_ADMIN,
    ROLE_COACH,
    ROLE_PLAYER,
    ROLE_SCOUT
} 