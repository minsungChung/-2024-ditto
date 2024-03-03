package org.example.global.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChartDto {
    private Long companyId;
    private LocalDate date;
    private Integer lastPrice;
    private Long tradingVolume;
}
