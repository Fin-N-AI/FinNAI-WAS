package com.finnai.domain.company.controller;

import com.finnai.domain.company.dto.response.CompanyOverviewResp;
import com.finnai.domain.company.dto.response.CompanySummariesResp;
import com.finnai.domain.company.dto.response.CompanySummaryResp;
import com.finnai.domain.company.dto.response.CompanyWatchListResp;
import com.finnai.domain.company.service.CompanyService;
import com.finnai.global.response.GlobalApiResponse;
import com.finnai.global.response.SuccessCode;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/company")
@RequiredArgsConstructor
public class CompanyControllerApi {

    private final CompanyService companyService;

    @GetMapping("/{id}/summary")
    public GlobalApiResponse<CompanySummaryResp> summary (@PathVariable("id") int companyId) {

        CompanySummaryResp summaryDto = companyService.summary(companyId);
        return GlobalApiResponse.success(SuccessCode.SUCCESS, summaryDto);
    }

    @GetMapping("/{id}/summaries")
    public GlobalApiResponse<CompanySummariesResp> summaries (@PathVariable("id") int companyId) {

        CompanySummariesResp summariesDto = companyService.summaries(companyId);
        return GlobalApiResponse.success(SuccessCode.SUCCESS, summariesDto);

    }

    @GetMapping("/{id}/overview")
    public GlobalApiResponse<CompanyOverviewResp> overview (@PathVariable("id") int companyId) {

        CompanyOverviewResp overviewDto = companyService.overview(companyId);
        return GlobalApiResponse.success(SuccessCode.SUCCESS, overviewDto);
    }

    @PostMapping("/{id}/watch-list")
    public GlobalApiResponse<CompanyWatchListResp> watchList (@PathVariable("id") int companyId){

        CompanyWatchListResp companyWatchListResp = companyService.watchList(companyId);
        return GlobalApiResponse.success(SuccessCode.SUCCESS, companyWatchListResp);
    }
}
