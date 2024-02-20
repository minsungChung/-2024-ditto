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

    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "twelve_days_avg", nullable = false)
    private int twelveDaysAvg;

    @Column(name = "twenty_days_avg", nullable = false)
    private int twentyDaysAvg;

    @Column(name = "twenty_six_days_avg", nullable = false)
    private int twentySixDaysAvg;

    @Builder
    public MovingAverageLine(Company company, int twelveDaysAvg, int twentyDaysAvg, int twentySixDaysAvg, LocalDate date){
        this.company = company;
        this.twelveDaysAvg = twelveDaysAvg;
        this.twentyDaysAvg = twentyDaysAvg;
        this.twentySixDaysAvg = twentySixDaysAvg;
        this.date = date;
    }

}
