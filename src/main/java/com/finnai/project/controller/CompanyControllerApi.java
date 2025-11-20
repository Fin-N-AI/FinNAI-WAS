package com.finnai.project.controller;


import com.finnai.project.dto.SummaryResponseDto;
import com.finnai.project.service.CompanySummaryInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/comapny")
@RequiredArgsConstructor
public class CompanyControllerApi {

    @GetMapping("/hello")
    public void Hello() {

    }


//    get : /api/v1/company/{id}/summary 단일 회사 단건 조회
    private final CompanySummaryInterface companySummary;

    @GetMapping("/{id}/summary")
    public SummaryResponseDto Summary (@PathVariable int id) {

        if (id <0 ) throw new IllegalArgumentException("hello excpetion");

        return companySummary.summary(id);


    }


}
