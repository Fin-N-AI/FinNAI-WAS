package com.finnai.domain.finance.controller;

import com.finnai.domain.finance.dto.response.FinancialIndicatorsResp;
import com.finnai.domain.finance.dto.response.FinancialsResp;
import com.finnai.domain.finance.service.FinancialService;
import com.finnai.global.response.GlobalApiResponse;
import com.finnai.global.response.SuccessCode;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/finance")
@RequiredArgsConstructor
public class FinancialControllerApi {

    private final FinancialService financialService;

    @GetMapping("/{id}/financials")
    public GlobalApiResponse<FinancialsResp> financial (@PathVariable("id") int companyId){
        FinancialsResp financialDto = financialService.financials(companyId);
        return GlobalApiResponse.success(SuccessCode.SUCCESS, financialDto);
    }

    @GetMapping("/{id}/indicators")
    public GlobalApiResponse<FinancialIndicatorsResp> indicators (@PathVariable("id") int companyId, @RequestParam String indicator){
        FinancialIndicatorsResp indicatorsDto = financialService.indicators(companyId, indicator);
        return GlobalApiResponse.success(SuccessCode.SUCCESS, indicatorsDto);
    }
}
