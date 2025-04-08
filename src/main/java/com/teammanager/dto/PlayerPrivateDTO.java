package com.teammanager.dto;

import lombok.Data;
import java.time.LocalDate;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class PlayerPrivateDTO extends PlayerPublicDTO {
    private LocalDate contractStartDate;
    private LocalDate contractEndDate;
    private Double contractValue;
    private Double contractFee;
} 