package org.example.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PricePerDayRepository extends JpaRepository<PricePerDay, Long> {

    List<PricePerDay> findTop25ByCompanyIdOrderByDateDesc(Long companyId);
    List<PricePerDay> findTop300ByCompanyIdOrderByDateDesc(Long companyId);
    List<PricePerDay> findTop900ByCompanyIdOrderByDateDesc(Long companyId);
    List<PricePerDay> findTop1500ByCompanyIdOrderByDateDesc(Long companyId);

    List<PricePerDay> findAllByCompanyIdOrderByDateAsc(Long companyId);
}
