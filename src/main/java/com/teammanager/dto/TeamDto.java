package com.teammanager.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TeamDto {
    private Long id;

    @NotBlank
    @Size(max = 100)
    private String name;

    @Size(max = 500)
    private String description;

    @NotBlank
    @Size(max = 50)
    private String city;

    @Size(max = 100)
    private String homeVenue;

    private LocalDateTime foundingDate;
    private Long coachId;
    private Long ownerId;
    private String logoUrl;
    private String primaryColor;
    private String secondaryColor;
} 