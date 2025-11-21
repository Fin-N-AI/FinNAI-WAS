package com.finnai.project.controller;


import com.finnai.project.dto.CompanySummariesDto;
import com.finnai.project.dto.CompanySummaryDto;
import com.finnai.project.service.CompanySummariesInterface;
import com.finnai.project.service.CompanySummaryInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/company")
@RequiredArgsConstructor
public class CompanyControllerApi {

    @GetMapping("/hello")
    public void hello() {

    }


//    get : /api/v1/company/{id}/summary 단일 회사 단건 조회
    private final CompanySummaryInterface companySummary;
    private final CompanySummariesInterface companySummaries;

    @GetMapping("/{id}/summary")
    public CompanySummaryDto summary (@PathVariable int id) {

        if (id <0 ) throw new IllegalArgumentException("hello excpetion");

        return companySummary.summary(id);


    }

    @GetMapping("/{id}/summaries")
    public CompanySummariesDto summaries (@PathVariable int id) {

        if (id <0 ) throw new IllegalArgumentException("hello excpetion");

        return companySummaries.summary(id);


    }


}
