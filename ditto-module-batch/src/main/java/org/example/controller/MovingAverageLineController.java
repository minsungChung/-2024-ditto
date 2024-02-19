package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.dto.MovingAvgLineDto;
import org.example.global.response.BaseResponse;
import org.example.service.MovingAverageLineService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/moving-avg-line")
public class MovingAverageLineController {

    private final MovingAverageLineService movingAverageLineService;

    @PostMapping
    public BaseResponse<MovingAvgLineDto> saveMovingAvgLine(){
        return new BaseResponse<>(movingAverageLineService.saveMovingAvgLine());
    }
}
