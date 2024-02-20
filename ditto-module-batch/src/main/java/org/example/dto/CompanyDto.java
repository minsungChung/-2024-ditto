package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.domain.Company;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CompanyDto {
    private Long id;
    private String stockName;
    private String itemCode;
    private String category;

    public CompanyDto(Company company){
        this(company.getId(), company.getCompanyName(), company.getItemCode(), company.getStockCategory());
    }
}
