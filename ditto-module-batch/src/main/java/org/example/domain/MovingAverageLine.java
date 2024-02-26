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
public class MovingAverageLine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "company_id", nullable = false)
    private Long companyId;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "twelve_days_avg", nullable = false)
    private int twelveDaysAvg;

    @Column(name = "twenty_days_avg", nullable = false)
    private int twentyDaysAvg;

    @Column(name = "twenty_six_days_avg", nullable = false)
    private int twentySixDaysAvg;

    @Builder
    public MovingAverageLine(Long companyId, int twelveDaysAvg, int twentyDaysAvg, int twentySixDaysAvg, LocalDate date){
        this.companyId = companyId;
        this.twelveDaysAvg = twelveDaysAvg;
        this.twentyDaysAvg = twentyDaysAvg;
        this.twentySixDaysAvg = twentySixDaysAvg;
        this.date = date;
    }

}
