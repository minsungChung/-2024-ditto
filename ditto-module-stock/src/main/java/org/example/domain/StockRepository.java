package org.example.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StockRepository extends JpaRepository<Company, Long> {
    Page<Company> findAllByStockCategory(String stockCategory, Pageable pageable);

    Optional<Company> findByCompanyName(String companyName);

    Optional<Company> findByItemCode(String itemCode);
}
