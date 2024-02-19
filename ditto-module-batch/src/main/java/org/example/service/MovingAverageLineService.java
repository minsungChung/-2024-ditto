package org.example.service;


import lombok.RequiredArgsConstructor;
import org.example.domain.MovingAverageLine;
import org.example.domain.PricePerDay;
import org.example.dto.MovingAvgLineDto;
import org.example.repository.MovingAvgLineRepository;
import org.example.repository.PricePerDayRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MovingAverageLineService {

    private final PricePerDayRepository pricePerDayRepository;
    private final MovingAvgLineRepository movingAvgLineRepository;
    public MovingAvgLineDto saveMovingAvgLine() {
        List<PricePerDay> priceList = pricePerDayRepository.findAllByCompanyIdOrderByDateAsc(1L);

        int twelve = 0;
        int twenty = 0;
        int twentySix = 0;

        List<Integer> prices = new ArrayList<>();
        for(int i = 0; i < priceList.size(); i++){
            PricePerDay ppd = priceList.get(i);
            prices.add(ppd.getLastPrice());
            twelve += ppd.getLastPrice();
            twenty += ppd.getLastPrice();
            twentySix += ppd.getLastPrice();

            if (i < 11){
                movingAvgLineRepository.save(MovingAverageLine.builder()
                        .company(ppd.getCompany())
                        .twelveDaysAvg(0)
                        .twentyDaysAvg(0)
                        .twentySixDaysAvg(0)
                        .date(ppd.getDate()).build());
            } else if (i < 19) {
                movingAvgLineRepository.save(MovingAverageLine.builder()
                        .company(ppd.getCompany())
                        .twelveDaysAvg(twelve/12)
                        .twentyDaysAvg(0)
                        .twentySixDaysAvg(0)
                        .date(ppd.getDate()).build());
                twelve -= prices.get(i-11);
            } else if (i < 25) {
                movingAvgLineRepository.save(MovingAverageLine.builder()
                        .company(ppd.getCompany())
                        .twelveDaysAvg(twelve/12)
                        .twentyDaysAvg(twenty/20)
                        .twentySixDaysAvg(0)
                        .date(ppd.getDate()).build());
                twelve -= prices.get(i-11);
                twenty -= prices.get(i-19);
            } else {
                movingAvgLineRepository.save(MovingAverageLine.builder()
                        .company(ppd.getCompany())
                        .twelveDaysAvg(twelve/12)
                        .twentyDaysAvg(twenty/20)
                        .twentySixDaysAvg(twentySix/26)
                        .date(ppd.getDate()).build());
                twelve -= prices.get(i-11);
                twenty -= prices.get(i-19);
                twentySix -= prices.get(i-25);
            }
        }
        return new MovingAvgLineDto(1L);
    }
}
