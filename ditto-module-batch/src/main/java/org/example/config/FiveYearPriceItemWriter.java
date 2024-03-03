package org.example.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.domain.PricePerDay;
import org.example.repository.PriceBulkRepository;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class FiveYearPriceItemWriter implements ItemWriter<List<PricePerDay>> {

    private final PriceBulkRepository priceBulkRepository;

    @Override
    public void write(Chunk<? extends List<PricePerDay>> chunk) throws Exception {
        long startTime = System.currentTimeMillis();
        int total = chunk.getItems().get(0).size();
        for (int i = 0; i < total; i+=999){
            if (i+999 > chunk.getItems().get(0).size()){
                priceBulkRepository.saveAll(chunk.getItems().get(0).subList(i, total-1));
            } else {
                log.info(String.valueOf(i));
                priceBulkRepository.saveAll(chunk.getItems().get(0).subList(i, i + 999));
            }
        }
        long stopTime = System.currentTimeMillis();
        log.info("코드 실행 시간: " + (stopTime - startTime));
    }
}
