package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.domain.PricePerDay;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ChartDto {
    private Long companyId;
    private LocalDate date;
    private Integer lastPrice;
    private Long tradingVolume;

    public ChartDto(PricePerDay pricePerDay){
        this(pricePerDay.getCompany().getId(), pricePerDay.getDate(), pricePerDay.getLastPrice(), pricePerDay.getTradingVolume());
    }
}
