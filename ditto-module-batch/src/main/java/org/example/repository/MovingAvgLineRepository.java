package org.example.repository;

import org.example.domain.MovingAverageLine;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovingAvgLineRepository extends JpaRepository<MovingAverageLine, Long> {
}
