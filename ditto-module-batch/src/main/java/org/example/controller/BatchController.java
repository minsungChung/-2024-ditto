package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.global.response.BaseResponse;
import org.example.service.BatchService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/batch")
public class BatchController {
    private final BatchService batchService;

    @PostMapping
    public BaseResponse<String> saveStockCodes() {
        return new BaseResponse<>(batchService.saveStockCodes());
    }
}
