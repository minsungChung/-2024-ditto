package org.example.repository;

import lombok.RequiredArgsConstructor;
import org.example.domain.PricePerDay;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class PriceBulkRepository {

    private final JdbcTemplate jdbcTemplate;

    @Transactional
    public void saveAll(List<PricePerDay> pricePerDays){
        String sql = "INSERT INTO PricePerDay (company_id,date,high_price,last_price,low_price,start_price,trading_volume) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";

        jdbcTemplate.batchUpdate(sql,
                new BatchPreparedStatementSetter() {
                    @Override
                    public void setValues(PreparedStatement ps, int i) throws SQLException {
                        PricePerDay pricePerDay = pricePerDays.get(i);
                        ps.setLong(1, pricePerDay.getCompanyId());
                        ps.setDate(2, Date.valueOf(pricePerDay.getDate()));
                        ps.setInt(3, pricePerDay.getHighPrice());
                        ps.setInt(4, pricePerDay.getLastPrice());
                        ps.setInt(5, pricePerDay.getLowPrice());
                        ps.setInt(6, pricePerDay.getStartPrice());
                        ps.setLong(7, pricePerDay.getTradingVolume());
                    }

                    @Override
                    public int getBatchSize() {
                        return pricePerDays.size();
                    }
                });
    }
}
