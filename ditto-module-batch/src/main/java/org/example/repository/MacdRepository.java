package org.example.repository;

import org.example.domain.Macd;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MacdRepository extends JpaRepository<Macd, Long> {
}
