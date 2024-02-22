package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.global.response.BaseResponse;
import org.example.service.BatchService;
import org.example.service.PriceService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/batch")
public class BatchController {
    private final BatchService batchService;
    private final PriceService priceService;
    @PostMapping
    public BaseResponse<String> saveStockCodes() {
        return new BaseResponse<>(batchService.saveStockCodes());
    }

    @PostMapping("/stocks/{itemCode}")
    public BaseResponse<List<String>> saveStockPrice(@PathVariable("itemCode") String itemCode) {
        return new BaseResponse<>(priceService.saveStockPrice(itemCode));
    }
}
