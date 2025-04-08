package com.teammanager.dto;

import com.teammanager.model.PlayerStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;

@Data
public class PlayerDto {
    private Long id;

    @NotBlank
    @Size(max = 50)
    private String firstName;

    @NotBlank
    @Size(max = 50)
    private String lastName;

    @NotNull
    private LocalDate dateOfBirth;

    @Size(max = 100)
    private String nationality;

    @Size(max = 20)
    private String jerseyNumber;

    @Size(max = 50)
    private String position;

    private Double height;
    private Double weight;

    @Size(max = 500)
    private String biography;

    private Long teamId;
    private String photoUrl;
    private PlayerStatus status;
    private LocalDate contractStartDate;
    private LocalDate contractEndDate;
} 