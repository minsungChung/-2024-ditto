package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.domain.Company;
import org.example.global.dto.CompanyDto;
import org.example.repository.CompanyBulkRepository;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class BatchService {
    private final String stockApi = "https://m.stock.naver.com/api/stocks/marketValue";
    private final CompanyBulkRepository companyBulkRepository;
    public String saveStockCodes() {
        List <Company> companyList = new ArrayList<>();
        for (int i = 1; i < 23; i++){
            String url = stockApi + "/KOSPI?page="+ i + "&pageSize=100";
            companyList.addAll(fetch(url).stream().map(c -> Company.builder()
                    .companyName(c.getStockName())
                    .itemCode(c.getItemCode())
                    .stockCategory("KOSPI").build()).collect(Collectors.toList()));
        }
        for (int i = 1; i < 19; i++){
            String url = stockApi + "/KOSDAQ?page="+ i + "&pageSize=100";
            companyList.addAll(fetch(url).stream().map(c -> Company.builder()
                    .companyName(c.getStockName())
                    .itemCode(c.getItemCode())
                    .stockCategory("KOSDAQ").build()).collect(Collectors.toList()));
        }
        companyBulkRepository.saveAll(companyList);
        return "저장 완료";
    }

    public List<CompanyDto> fetch(String url){
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<?> entity = new HttpEntity<>(new HttpHeaders());
        ResponseEntity<Map> resultMap = restTemplate.exchange(url, HttpMethod.GET, entity, Map.class);
        List<?> lst = (List)resultMap.getBody().get("stocks");

        return lst.stream().map(s -> {
            Map sa = (Map)s;
            return new CompanyDto(0L, sa.get("stockName").toString(), sa.get("itemCode").toString(), "");
        }).collect(Collectors.toList());
    }
}
