package com.teammanager.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class PlayerPublicDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String jerseyNumber;
    private String position;
    private LocalDate dateOfBirth;
    private String nationality;
    private Double height;
    private Double weight;
    private String status;
    private Long teamId;
} 