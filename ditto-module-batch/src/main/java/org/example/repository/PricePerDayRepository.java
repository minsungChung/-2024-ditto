package org.example.repository;

import org.example.domain.PricePerDay;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PricePerDayRepository extends JpaRepository<PricePerDay, Long> {
}
