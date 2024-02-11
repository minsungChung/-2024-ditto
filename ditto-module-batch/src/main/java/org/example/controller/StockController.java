package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.dto.CompanyDto;
import org.example.global.response.BaseResponse;
import org.example.service.StockService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/stocks")
public class StockController {

    private final StockService stockService;

    @GetMapping("/kospi")
    public BaseResponse<Page<CompanyDto>> showKospiStocks(@PageableDefault(size = 100) Pageable pageable){
        return new BaseResponse<>(stockService.findKospiStocks(pageable));
    }

    @GetMapping("/kosdaq")
    public BaseResponse<Page<CompanyDto>> showKosdaqStocks(@PageableDefault(size = 100) Pageable pageable){
        return new BaseResponse<>(stockService.findKosdaqStocks(pageable));
    }

    @GetMapping("/name/{stockName}")
    public BaseResponse<CompanyDto> findStockByName(@PathVariable("stockName") String stockName){
        return new BaseResponse<>(stockService.findStockByName(stockName));
    }

    @GetMapping("/code/{itemCode}")
    public BaseResponse<CompanyDto> findStockByCode(@PathVariable("itemCode") String itemCode){
        return new BaseResponse<>(stockService.findStockByCode(itemCode));
    }

    @GetMapping("/kospi/name/asc")
    public BaseResponse<Page<CompanyDto>> showKospiStocksByNameAsc(@PageableDefault(size = 100, sort = "companyName", direction = Sort.Direction.ASC) Pageable pageable){
        return new BaseResponse<>(stockService.findKospiStocks(pageable));
    }
    @GetMapping("/kospi/name/desc")
    public BaseResponse<Page<CompanyDto>> showKospiStocksByNameDesc(@PageableDefault(size = 100, sort = "companyName", direction = Sort.Direction.DESC) Pageable pageable){
        return new BaseResponse<>(stockService.findKospiStocks(pageable));
    }

    @GetMapping("/kospi/code/asc")
    public BaseResponse<Page<CompanyDto>> showKospiStocksByCodeAsc(@PageableDefault(size = 100, sort = "itemCode", direction = Sort.Direction.ASC) Pageable pageable){
        return new BaseResponse<>(stockService.findKospiStocks(pageable));
    }
    @GetMapping("/kospi/code/desc")
    public BaseResponse<Page<CompanyDto>> showKospiStocksByCodeDesc(@PageableDefault(size = 100, sort = "itemCode", direction = Sort.Direction.DESC) Pageable pageable){
        return new BaseResponse<>(stockService.findKospiStocks(pageable));
    }

    @GetMapping("/kosdaq/name/asc")
    public BaseResponse<Page<CompanyDto>> showKosdaqStocksByNameAsc(@PageableDefault(size = 100, sort = "companyName", direction = Sort.Direction.ASC) Pageable pageable){
        return new BaseResponse<>(stockService.findKosdaqStocks(pageable));
    }

    @GetMapping("/kosdaq/name/desc")
    public BaseResponse<Page<CompanyDto>> showKosdaqStocksByNameDesc(@PageableDefault(size = 100, sort = "companyName", direction = Sort.Direction.DESC) Pageable pageable){
        return new BaseResponse<>(stockService.findKosdaqStocks(pageable));
    }

    @GetMapping("/kosdaq/code/asc")
    public BaseResponse<Page<CompanyDto>> showKosdaqStocksByCodeAsc(@PageableDefault(size = 100, sort = "itemCode", direction = Sort.Direction.ASC) Pageable pageable){
        return new BaseResponse<>(stockService.findKosdaqStocks(pageable));
    }

    @GetMapping("/kosdaq/code/desc")
    public BaseResponse<Page<CompanyDto>> showKosdaqStocksByCodeDesc(@PageableDefault(size = 100, sort = "itemCode", direction = Sort.Direction.DESC) Pageable pageable){
        return new BaseResponse<>(stockService.findKosdaqStocks(pageable));
    }

}
