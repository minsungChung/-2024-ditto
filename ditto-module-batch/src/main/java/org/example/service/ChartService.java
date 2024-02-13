package org.example.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.domain.Company;
import org.example.domain.PricePerDay;
import org.example.dto.ChartDto;
import org.example.global.exception.NoSuchCompanyException;
import org.example.repository.CompanyRepository;
import org.example.repository.PricePerDayRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChartService {
    private final CompanyRepository companyRepository;
    private final PricePerDayRepository pricePerDayRepository;
    public List<ChartDto> drawMonthlyChart(String itemCode) {
        Company company = companyRepository.findByItemCode(itemCode).orElseThrow(() -> {
            throw new NoSuchCompanyException();
        });

        List<PricePerDay> monthlyPriceList = pricePerDayRepository.findTop25ByCompanyIdOrderByDateDesc(company.getId());

        return monthlyPriceList.stream().map(res -> new ChartDto(res)).collect(Collectors.toList());
    }

    public List<ChartDto> drawYearlyChart(String itemCode, int num) {
        Company company = companyRepository.findByItemCode(itemCode).orElseThrow(() -> {
            throw new NoSuchCompanyException();
        });

        List<PricePerDay> yearlyPriceList = null;

        if (num == 1){
            yearlyPriceList = pricePerDayRepository.findTop300ByCompanyIdOrderByDateDesc(company.getId());
        } else if (num == 3) {
            yearlyPriceList = pricePerDayRepository.findTop900ByCompanyIdOrderByDateDesc(company.getId());
        } else if (num == 5) {
            yearlyPriceList = pricePerDayRepository.findTop1500ByCompanyIdOrderByDateDesc(company.getId());
        }


        return yearlyPriceList.stream().map(res -> new ChartDto(res)).collect(Collectors.toList());
    }
}
