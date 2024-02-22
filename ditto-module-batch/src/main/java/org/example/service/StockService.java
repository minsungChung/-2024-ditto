package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.domain.Company;
import org.example.dto.CompanyDto;
import org.example.global.dto.StockDto;
import org.example.global.exception.NoSuchCompanyException;
import org.example.repository.StockRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StockService {

    private final StockRepository stockRepository;

    public Page<CompanyDto> findStocks(String category, Pageable pageable) {
        if (category.equals("kospi")){
            return stockRepository.findAllByStockCategory("KOSPI", pageable).map(company -> new CompanyDto(company));
        } else if (category.equals("kosdaq")){
            return stockRepository.findAllByStockCategory("KOSDAQ", pageable).map(company -> new CompanyDto(company));
        } else {
            throw new RuntimeException("잘못된 URL 입니다.");
        }
    }

    public CompanyDto findStockByName(String stockName) {
        Company company = stockRepository.findByCompanyName(stockName).orElseThrow(()-> {throw new NoSuchCompanyException();});

        return new CompanyDto(company);
    }

    public CompanyDto findStockByCode(String itemCode) {
        Company company = stockRepository.findByItemCode(itemCode).orElseThrow(() -> {throw new NoSuchCompanyException();});
        return new CompanyDto(company);
    }

    public StockDto findStockById(Long stockId){
        Company company = stockRepository.findById(stockId).orElseThrow(() -> {
            throw new NoSuchCompanyException();
        });
        return StockDto.builder()
                .id(company.getId())
                .stockName(company.getCompanyName())
                .itemCode(company.getItemCode())
                .category(company.getStockCategory()).build();
    }
}
