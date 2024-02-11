package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.domain.Company;
import org.example.dto.CompanyDto;
import org.example.global.exception.NoSuchCompanyException;
import org.example.repository.StockRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StockService {

    private final StockRepository stockRepository;

    public Page<CompanyDto> findKospiStocks(Pageable pageable) {
        return stockRepository.findAllByStockCategory("KOSPI", pageable).map(company -> new CompanyDto(company));
    }

    public Page<CompanyDto> findKosdaqStocks(Pageable pageable) {
        return stockRepository.findAllByStockCategory("KOSDAQ", pageable).map(company -> new CompanyDto(company));
    }

    public CompanyDto findStockByName(String stockName) {
        Company company = stockRepository.findByCompanyName(stockName).orElseThrow(()-> {throw new NoSuchCompanyException();});

        return new CompanyDto(company);
    }

    public CompanyDto findStockByCode(String itemCode) {
        Company company = stockRepository.findByItemCode(itemCode).orElseThrow(() -> {throw new NoSuchCompanyException();});
        return new CompanyDto(company);
    }
}
