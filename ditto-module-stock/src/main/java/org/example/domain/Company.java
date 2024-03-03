package org.example.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "company_name", nullable = false)
    private String companyName;

    @Column(name = "item_code", nullable = false)
    private String itemCode;

    @Column(name = "stock_category")
    private String stockCategory;

    @Builder
    public Company(String companyName, String itemCode, String stockCategory){
        this.companyName = companyName;
        this.itemCode = itemCode;
        this.stockCategory = stockCategory;
    }
}
