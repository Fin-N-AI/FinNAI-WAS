package com.finnai.project.controller;


import com.finnai.project.dto.*;
import com.finnai.project.service.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/company")
@RequiredArgsConstructor
public class CompanyControllerApi {

    private final CompanySummaryInterface companySummary;
    private final CompanySummariesInterface companySummaries;
    private final CompanyOverviewInterface companyOverview;
    private final CompanyFinancialsInterface companyFinancials;
    private final CompanyNewsInterface companyNews;
    private final CompanyReportInterface companyReport;
    private final CompanyIndicatorsInterface companyIndicators;
    private final CompanyWatchListInterface companyWatchList;

    @GetMapping("/hello")
    public void hello() {

    }


//    get : /api/v1/company/{id}/summary 단일 회사 단건 조회

    @GetMapping("/{id}/summary")
    public CompanySummaryDto summary (@PathVariable("id") int companyId) {

        if (companyId <0 ) throw new IllegalArgumentException("회사 번호가 0미만으로 올바른 값이 아닙니다.");

        return companySummary.summary(companyId);


    }

    @GetMapping("/{id}/summaries")
    public CompanySummariesDto summaries (@PathVariable("id") int companyId) {

        if (companyId <0 ) throw new IllegalArgumentException("회사 번호가 0미만으로 올바른 값이 아닙니다.");
        return companySummaries.summary(companyId);

    }

    @GetMapping("/{id}/overview")
    public CompanyOverviewDto overview (@PathVariable("id") int companyId) {

        if (companyId <0 ) throw new IllegalArgumentException("회사 번호가 0 미만으로 올바른 값이 아닙니다.");
        return companyOverview.overview(companyId);

    }

    @GetMapping("/{id}/financials")
    public CompanyFinancialsDto financial (@PathVariable("id") int companyId){
        if (companyId <0 ) throw new IllegalArgumentException("회사 번호가 0미만으로 올바른 값이 아닙니다.");
        return companyFinancials.getFinancial(companyId);

    }

    @GetMapping("/{id}/news")
    public CompanyNewsDto news (@PathVariable("id") int companyId) {
        if (companyId <0 ) throw new IllegalArgumentException("회사 번호가 0미만으로 올바른 값이 아닙니다.");
        return companyNews.news(companyId);
    }

    @GetMapping("/{id}/report")
    public  CompanyReportDto report (@PathVariable("id") int companyId) {
        if (companyId <0 ) throw new IllegalArgumentException("회사 번호가 0미만으로 올바른 값이 아닙니다.");
        return companyReport.report(companyId);

    }

    @GetMapping("/{id}/indicators")
    public CompanyIndicatorsDto indicators (@PathVariable("id") int companyId , @RequestParam String indicator ){

        if (companyId <0 ) throw new IllegalArgumentException("회사 번호가 0미만으로 올바른 값이 아닙니다.");
        return companyIndicators.getIndicators(companyId, indicator);
    }


    @PostMapping("/{id}/watch-list")
    public CompanyWatchListRequestDto watchList (@PathVariable("id") int companyId){

        if (companyId <0 ) throw new IllegalArgumentException("회사 번호가 0미만으로 올바른 값이 아닙니다.");
        return companyWatchList.watchList(companyId);
    }

}
