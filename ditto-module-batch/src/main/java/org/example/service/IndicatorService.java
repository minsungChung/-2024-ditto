package org.example.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.domain.BollingerBand;
import org.example.domain.Macd;
import org.example.domain.MovingAverageLine;
import org.example.domain.PricePerDay;
import org.example.dto.IndicatorDto;
import org.example.repository.BollingerBandRepository;
import org.example.repository.MacdRepository;
import org.example.repository.MovingAvgLineRepository;
import org.example.repository.PricePerDayRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class IndicatorService {

    private final PricePerDayRepository pricePerDayRepository;
    private final MovingAvgLineRepository movingAvgLineRepository;
    private final BollingerBandRepository bollingerBandRepository;
    private final MacdRepository macdRepository;
    public IndicatorDto saveIndicators() {
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

            if(i >= 19){
                List<Double> bands = calculateBand(prices, twenty / 20, i);
                bollingerBandRepository.save(BollingerBand.builder()
                        .companyId(ppd.getCompanyId())
                        .date(ppd.getDate())
                        .middleBand(twenty/20)
                        .upperBand(bands.get(0))
                        .lowerBand(bands.get(1)).build());
            }

            if (i < 11){
                movingAvgLineRepository.save(MovingAverageLine.builder()
                        .companyId(ppd.getCompanyId())
                        .twelveDaysAvg(0)
                        .twentyDaysAvg(0)
                        .twentySixDaysAvg(0)
                        .date(ppd.getDate()).build());
            } else if (i < 19) {
                movingAvgLineRepository.save(MovingAverageLine.builder()
                        .companyId(ppd.getCompanyId())
                        .twelveDaysAvg(twelve/12)
                        .twentyDaysAvg(0)
                        .twentySixDaysAvg(0)
                        .date(ppd.getDate()).build());
                twelve -= prices.get(i-11);
            } else if (i < 25) {
                movingAvgLineRepository.save(MovingAverageLine.builder()
                        .companyId(ppd.getCompanyId())
                        .twelveDaysAvg(twelve/12)
                        .twentyDaysAvg(twenty/20)
                        .twentySixDaysAvg(0)
                        .date(ppd.getDate()).build());
                twelve -= prices.get(i-11);
                twenty -= prices.get(i-19);
            } else {
                movingAvgLineRepository.save(MovingAverageLine.builder()
                        .companyId(ppd.getCompanyId())
                        .twelveDaysAvg(twelve/12)
                        .twentyDaysAvg(twenty/20)
                        .twentySixDaysAvg(twentySix/26)
                        .date(ppd.getDate()).build());
                twelve -= prices.get(i-11);
                twenty -= prices.get(i-19);
                twentySix -= prices.get(i-25);
            }
        }

        List<Integer> macdLine = calculateMACDLine(prices);
        List<Double> signalLine = calculateSignalLine(macdLine);

        for (int i = 0; i < macdLine.size(); i++){
            macdRepository.save(Macd.builder()
                    .companyId(priceList.get(0).getCompanyId())
                    .date(priceList.get(0).getDate())
                    .macdLine(macdLine.get(i))
                    .signalLine(signalLine.get(i)).build());
        }
        return new IndicatorDto(1L);
    }

    private List<Double> calculateBand(List<Integer> prices, double mean, int endIndex){
        List<Double> bands = new ArrayList<>();
        double standardDeviation = calculateStandardDeviation(prices, mean, endIndex);
        bands.add(mean + 2*standardDeviation);
        bands.add(mean - 2*standardDeviation);
        return bands;
    }


    private double calculateStandardDeviation(List<Integer> prices, double mean, int endIndex){
        int sum = 0;
        for(int i = endIndex-19; i <= endIndex; i++){
            sum += Math.pow(prices.get(i) - mean, 2);
        }

        return Math.sqrt(sum / 20);
    }

    private List<Integer> calculateMACDLine(List<Integer> prices){
        List<Double> fastMA = calculateExponentialMovingAverage(prices, 12);
        List<Double> slowMA = calculateExponentialMovingAverage(prices, 26);

        List<Integer> macdLine = new ArrayList<>();
        for (int i = 0; i < fastMA.size(); i++){
            macdLine.add((int) (fastMA.get(i) - slowMA.get(i)));
        }

        return macdLine;
    }

    private List<Double> calculateSignalLine(List<Integer> macdLine){
        return calculateExponentialMovingAverage(macdLine, 9);
    }

    private List<Double> calculateExponentialMovingAverage(List<Integer> prices, int period){
        List<Double> ema = new ArrayList<>();
        double multiplier = 2.0 / (period + 1);

        ema.add(Double.valueOf(prices.get(0)));

        for(int i = 1; i < prices.size(); i++){
            double currentEMA = (prices.get(i) - ema.get(i - 1)) * multiplier + ema.get(i - 1);
            ema.add(currentEMA);
        }

        return ema;
    }
}
