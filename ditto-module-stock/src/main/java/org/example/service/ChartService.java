package org.example.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.domain.Company;
import org.example.domain.CompanyRepository;
import org.example.domain.PricePerDay;
import org.example.domain.PricePerDayRepository;
import org.example.global.dto.ChartDto;
import org.example.global.exception.NoSuchCompanyException;

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

        return monthlyPriceList.stream().map(res -> ChartDto.builder()
                .companyId(res.getCompanyId())
                .lastPrice(res.getLastPrice())
                .tradingVolume(res.getTradingVolume())
                .date(res.getDate()).build()).collect(Collectors.toList());
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


        return yearlyPriceList.stream().map(res -> ChartDto.builder()
                .companyId(res.getCompanyId())
                .lastPrice(res.getLastPrice())
                .tradingVolume(res.getTradingVolume())
                .date(res.getDate()).build()).collect(Collectors.toList());
    }
}
