package org.example.repository;

import lombok.RequiredArgsConstructor;
import org.example.domain.Company;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class CompanyBulkRepository {

    private final JdbcTemplate jdbcTemplate;

    @Transactional
    public void saveAll(List<Company> companyList){
        String sql = "INSERT INTO Company (company_name, item_code, stock_category) " +
                "VALUES (?, ?, ?)";

        jdbcTemplate.batchUpdate(sql,
                new BatchPreparedStatementSetter() {
                    @Override
                    public void setValues(PreparedStatement ps, int i) throws SQLException {
                        Company company = companyList.get(i);
                        ps.setString(1, company.getCompanyName());
                        ps.setString(2, company.getItemCode());
                        ps.setString(3, company.getStockCategory());
                    }

                    @Override
                    public int getBatchSize() {
                        return companyList.size();
                    }
                });
    }
}
