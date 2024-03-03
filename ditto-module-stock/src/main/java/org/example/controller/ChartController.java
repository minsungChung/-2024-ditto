package org.example.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.global.dto.ChartDto;
import org.example.global.response.BaseResponse;
import org.example.service.ChartService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/chart/{itemCode}")
public class ChartController {
    private final ChartService chartService;

    @GetMapping("/month")
    public BaseResponse<List<ChartDto>> drawMonthlyChart(@PathVariable("itemCode") String itemCode){
        log.info(itemCode);
        return new BaseResponse<>(chartService.drawMonthlyChart(itemCode));
    }

    @GetMapping("/year/{num}")
    public BaseResponse<List<ChartDto>> drawYearlyChart(@PathVariable("itemCode") String itemCode, @PathVariable("num") int num){
        return new BaseResponse<>(chartService.drawYearlyChart(itemCode, num));
    }
}
