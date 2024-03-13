package org.example.controller.api;

import org.example.global.dto.StockDto;
import org.example.global.response.BaseResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "stockClient", url = "http://stock:8086/api/stocks")
public interface StockApi {
    @GetMapping("/{stockId}")
    BaseResponse<StockDto> findByStockId(@PathVariable("stockId") Long stockId);
}
