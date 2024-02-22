package org.example.repository;

import org.example.domain.BollingerBand;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BollingerBandRepository extends JpaRepository<BollingerBand, Long> {
}
