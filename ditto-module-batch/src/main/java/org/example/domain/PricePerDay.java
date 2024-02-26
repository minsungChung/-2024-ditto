package org.example.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PricePerDay {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "company_id", nullable = false)
    private Long companyId;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "start_price")
    private Integer startPrice;

    @Column(name = "high_price")
    private Integer highPrice;

    @Column(name = "low_price")
    private Integer lowPrice;

    @Column(name = "last_price")
    private Integer lastPrice;

    @Column(name = "trading_volume")
    private Long tradingVolume;

    @Builder
    public PricePerDay(Long companyId, LocalDate date, Integer startPrice, Integer highPrice, Integer lowPrice, Integer lastPrice, Long tradingVolume){
        this.companyId = companyId;
        this.date = date;
        this.startPrice = startPrice;
        this.highPrice = highPrice;
        this.lowPrice = lowPrice;
        this.lastPrice = lastPrice;
        this.tradingVolume = tradingVolume;
    }

}
