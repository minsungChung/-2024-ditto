package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.global.dto.CompanyDto;
import org.example.global.dto.StockDto;
import org.example.global.response.BaseResponse;
import org.example.service.StockService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/stocks")
public class StockController {

    private final StockService stockService;

    @GetMapping("{stockId}")
    public BaseResponse<StockDto> findByStockId(@PathVariable("stockId") Long stockId){
        return new BaseResponse<>(stockService.findStockById(stockId));
    }
    @GetMapping
    public BaseResponse<Page<CompanyDto>> showStocks(@RequestParam String category,
                                                     @RequestParam(required = false) String name,
                                                     @RequestParam(required = false) String code){
        Pageable pageable = null;
        if (name == null && code == null){
            pageable = PageRequest.of(0, 100);
        } else if (name != null && name.equals("asc")){
            pageable = PageRequest.of(0, 100, Sort.by("companyName").ascending());
        } else if (name != null && name.equals("desc")) {
            pageable = PageRequest.of(0, 100, Sort.by("companyName").descending());
        } else if (code != null && code.equals("asc")) {
            pageable = PageRequest.of(0, 100, Sort.by("itemCode").ascending());
        } else if (code != null &&code.equals("desc")) {
            pageable = PageRequest.of(0, 100, Sort.by("itemCode").descending());
        }
        return new BaseResponse<>(stockService.findStocks(category, pageable));
    }

    @GetMapping("/name/{stockName}")
    public BaseResponse<CompanyDto> findStockByName(@PathVariable("stockName") String stockName){
        return new BaseResponse<>(stockService.findStockByName(stockName));
    }

    @GetMapping("/code/{itemCode}")
    public BaseResponse<CompanyDto> findStockByCode(@PathVariable("itemCode") String itemCode){
        return new BaseResponse<>(stockService.findStockByCode(itemCode));
    }

}
