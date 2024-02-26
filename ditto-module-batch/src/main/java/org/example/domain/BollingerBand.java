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
public class BollingerBand {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "company_id", nullable = false)
    private Long companyId;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "middle_band", nullable = false)
    private double middleBand;

    @Column(name = "upper_band", nullable = false)
    private double upperBand;

    @Column(name = "lower_band", nullable = false)
    private double lowerBand;

    @Builder
    public BollingerBand(Long companyId, LocalDate date, double middleBand, double upperBand, double lowerBand){
        this.companyId = companyId;
        this.date = date;
        this.middleBand = middleBand;
        this.upperBand = upperBand;
        this.lowerBand = lowerBand;
    }
}
