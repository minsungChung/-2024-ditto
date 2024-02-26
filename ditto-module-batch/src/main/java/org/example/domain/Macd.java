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
public class Macd {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "company_id", nullable = false)
    private Long companyId;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "macd_line", nullable = false)
    private int macdLine;

    @Column(name = "signal_line", nullable = false)
    private double signalLine;
    @Builder
    public Macd(Long companyId, LocalDate date, int macdLine, double signalLine){
        this.companyId = companyId;
        this.date = date;
        this.macdLine = macdLine;
        this.signalLine = signalLine;
    }
}
