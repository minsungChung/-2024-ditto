package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.dto.IndicatorDto;
import org.example.global.response.BaseResponse;
import org.example.service.IndicatorService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/indicators")
public class IndicatorController {

    private final IndicatorService indicatorService;

    @PostMapping
    public BaseResponse<IndicatorDto> saveIndicators(){
        return new BaseResponse<>(indicatorService.saveIndicators());
    }
}
