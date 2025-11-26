package com.finnai.project.controller;


import com.finnai.project.dto.*;
import com.finnai.project.response.GlobalApiResponse;
import com.finnai.project.response.SuccessCode;
import com.finnai.project.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/company")
@RequiredArgsConstructor
public class CompanyControllerApi {

    private final CompanySummaryService companySummary;
    private final CompanySummariesService companySummaries;
    private final CompanyOverviewService companyOverview;
    private final CompanyFinancialsService companyFinancials;
    private final CompanyNewsService companyNews;
    private final CompanyReportService companyReport;
    private final CompanyIndicatorsService companyIndicators;
    private final CompanyWatchListService companyWatchList;



//    get : /api/v1/company/{id}/summary 단일 회사 단건 조회

    @GetMapping("/{id}/summary")
    public GlobalApiResponse<CompanySummaryDto> summary (@PathVariable("id") int companyId) {

        CompanySummaryDto summaryDto = companySummary.summary(companyId);


        return GlobalApiResponse.success(SuccessCode.SUCCESS, summaryDto);


    }

    @GetMapping("/{id}/summaries")
    public GlobalApiResponse<CompanySummariesDto> summaries (@PathVariable("id") int companyId) {

        CompanySummariesDto summariesDto = companySummaries.summary(companyId);

        return GlobalApiResponse.success(SuccessCode.SUCCESS, summariesDto);

    }

    @GetMapping("/{id}/overview")
    public GlobalApiResponse<CompanyOverviewDto> overview (@PathVariable("id") int companyId) {

        CompanyOverviewDto overviewDto = companyOverview.overview(companyId);
        return GlobalApiResponse.success(SuccessCode.SUCCESS, overviewDto);
    }

    @GetMapping("/{id}/financials")
    public GlobalApiResponse<CompanyFinancialsDto> financial (@PathVariable("id") int companyId){

        CompanyFinancialsDto financialDto = companyFinancials.getFinancial(companyId);

        return GlobalApiResponse.success(SuccessCode.SUCCESS, financialDto);

    }

    @GetMapping("/{id}/news")
    public GlobalApiResponse<CompanyNewsDto> news (@PathVariable("id") int companyId) {
        CompanyNewsDto newsDto = companyNews.news(companyId);


        return GlobalApiResponse.success(SuccessCode.SUCCESS, newsDto);
    }

    @GetMapping("/{id}/report")
    public  GlobalApiResponse<CompanyReportDto> report (@PathVariable("id") int companyId) {

        CompanyReportDto reportDto = companyReport.report(companyId);


        return GlobalApiResponse.success(SuccessCode.SUCCESS, reportDto);

    }

    @GetMapping("/{id}/indicators")
    public GlobalApiResponse<CompanyIndicatorsDto> indicators (@PathVariable("id") int companyId , @RequestParam String indicator ){

        CompanyIndicatorsDto indicatorsDto = companyIndicators.getIndicators(companyId, indicator);

        return GlobalApiResponse.success(SuccessCode.SUCCESS, indicatorsDto);
    }


    @PostMapping("/{id}/watch-list")
    public GlobalApiResponse<CompanyWatchListRequestDto> watchList (@PathVariable("id") int companyId){

        CompanyWatchListRequestDto companyWatchListRequestDto = companyWatchList.watchList(companyId);


        return GlobalApiResponse.success(SuccessCode.SUCCESS, companyWatchListRequestDto);
    }

}
