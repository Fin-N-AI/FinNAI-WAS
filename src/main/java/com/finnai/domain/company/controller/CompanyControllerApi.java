package com.finnai.domain.company.controller;

import com.finnai.domain.company.dto.response.CompanySummariesResp;
import com.finnai.domain.company.dto.response.CompanySummaryResp;
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

    //기업 ID를 통해 기업 정보 조회
    @GetMapping("/{companyId}/summary")
    public GlobalApiResponse<CompanySummaryResp> summary (@PathVariable("companyId") int companyId) {

        CompanySummaryResp summaryDto = companyService.summary(companyId);
        return GlobalApiResponse.success(SuccessCode.SUCCESS, summaryDto);
    }


    //메인페이지 -> 기업 정보 pageable로 가져오기
    @GetMapping("/summaries")
    public GlobalApiResponse<CompanySummariesResp> summaries (
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ) {
        CompanySummariesResp summaries = companyService.summaries(page, size);
        return GlobalApiResponse.success(SuccessCode.SUCCESS, summaries);
    }

//    //company table의 overview
//    @GetMapping("/{companyId}/overview")
//    public GlobalApiResponse<CompanyOverviewResp> overview (@PathVariable("companyId") int companyId) {
//
//        CompanyOverviewResp overviewDto = companyService.overview(companyId);
//        return GlobalApiResponse.success(SuccessCode.SUCCESS, overviewDto);
//    }


    //북마크 요청
//    @PostMapping("/{id}/watch-list")
//    public GlobalApiResponse<CompanyWatchListResp> watchList (@PathVariable("id") int companyId){
//
//        CompanyWatchListResp companyWatchListResp = companyService.watchList(companyId);
//        return GlobalApiResponse.success(SuccessCode.SUCCESS, companyWatchListResp);
//    }
}
