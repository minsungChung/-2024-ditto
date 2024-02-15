package org.example.config;

import lombok.RequiredArgsConstructor;
import org.example.domain.PricePerDay;
import org.example.repository.PricePerDayRepository;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;

import java.util.List;

@RequiredArgsConstructor
public class PriceItemWriter implements ItemWriter<List<PricePerDay>> {

    private final PricePerDayRepository pricePerDayRepository;

    @Override
    public void write(Chunk<? extends List<PricePerDay>> chunk) throws Exception {
        pricePerDayRepository.saveAll(chunk.getItems().get(0));
    }
}
